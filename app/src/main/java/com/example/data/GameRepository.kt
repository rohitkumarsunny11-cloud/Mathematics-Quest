package com.example.data

import com.example.data.local.AchievementEntity
import com.example.data.local.DailyProgressEntity
import com.example.data.local.GameDao
import com.example.data.local.LevelProgressEntity
import com.example.data.local.MathCrossProgressEntity
import com.example.data.local.UserStatsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class GameRepository(private val dao: GameDao) {

    private val mathCrossMutex = Mutex()
    private val hintDeductMutex = Mutex()

    val userStats: Flow<UserStatsEntity> = dao.getUserStats().map { it ?: UserStatsEntity() }
    val levelProgressList: Flow<List<LevelProgressEntity>> = dao.getAllLevelProgress()
    val mathCrossProgressList: Flow<List<MathCrossProgressEntity>> = dao.getAllMathCrossProgress()
    val achievements: Flow<List<AchievementEntity>> = dao.getAllAchievements()

    fun getDailyProgressForDate(dateString: String): Flow<List<DailyProgressEntity>> {
        return dao.getDailyProgress(dateString)
    }

    suspend fun initializeIfNeeded() {
        val currentStats = dao.getUserStatsDirect()
        if (currentStats == null) {
            dao.insertUserStats(UserStatsEntity())
        }

        // Initialize default achievements if empty
        val existingAchievements = dao.getAllAchievements().firstOrNull() ?: emptyList()
        if (existingAchievements.isEmpty()) {
            dao.insertAchievements(defaultAchievements)
        }
    }

    suspend fun getHighestUnlockedLevel(): Int {
        val progress = dao.getAllLevelProgress().firstOrNull() ?: emptyList()
        val completedIds = progress.filter { it.completed }.map { it.levelId }
        val maxCompleted = completedIds.maxOrNull() ?: 0
        return maxCompleted + 1
    }

    suspend fun recordLevelCompletion(
        levelId: Int,
        timeSeconds: Int,
        hintsUsedCount: Int,
        coinsEarned: Int
    ): LevelCompletionResult {
        val existing = dao.getLevelProgress(levelId)

        // Calculate stars:
        // 0 hints used & fast: 3 stars
        // 1 hint used: 2 stars
        // 2+ hints used: 1 star
        val earnedStars = when {
            hintsUsedCount == 0 -> 3
            hintsUsedCount == 1 -> 2
            else -> 1
        }

        val bestStars = maxOf(existing?.stars ?: 0, earnedStars)
        val bestTime = if (existing != null && existing.bestTimeSeconds > 0) {
            minOf(existing.bestTimeSeconds, timeSeconds)
        } else {
            timeSeconds
        }

        val isFirstTime = existing == null || !existing.completed

        dao.insertLevelProgress(
            LevelProgressEntity(
                levelId = levelId,
                stars = bestStars,
                completed = true,
                bestTimeSeconds = bestTime,
                hintsUsed = hintsUsedCount,
                completedTimestamp = System.currentTimeMillis()
            )
        )

        // Update User Stats
        val stats = dao.getUserStatsDirect() ?: UserStatsEntity()
        val updatedCoins = if (isFirstTime) stats.coins + coinsEarned else stats.coins + (coinsEarned / 4)
        val updatedCorrect = stats.totalCorrectAnswers + 1

        dao.insertUserStats(
            stats.copy(
                coins = updatedCoins,
                totalCorrectAnswers = updatedCorrect
            )
        )

        // Check achievements progress
        checkLevelAchievements()

        return LevelCompletionResult(
            stars = earnedStars,
            coinsAwarded = if (isFirstTime) coinsEarned else (coinsEarned / 4),
            isFirstCompletion = isFirstTime
        )
    }

    suspend fun deductCoinsForHint(cost: Int): Boolean {
        return hintDeductMutex.withLock {
            val stats = dao.getUserStatsDirect() ?: UserStatsEntity()
            if (stats.coins < cost) return@withLock false
            dao.insertUserStats(
                stats.copy(
                    coins = stats.coins - cost,
                    totalHintsUsed = stats.totalHintsUsed + 1
                )
            )
            true
        }
    }

    suspend fun recordWrongAnswer() {
        val stats = dao.getUserStatsDirect() ?: UserStatsEntity()
        dao.insertUserStats(stats.copy(totalWrongAnswers = stats.totalWrongAnswers + 1))
    }

    suspend fun recordDailyPuzzleSolved(dateString: String, index: Int, coinsReward: Int = 15) {
        val id = "$dateString-$index"
        dao.insertDailyProgress(
            DailyProgressEntity(
                id = id,
                dateString = dateString,
                puzzleIndex = index,
                completed = true
            )
        )

        val stats = dao.getUserStatsDirect() ?: UserStatsEntity()
        val dailyCompleted = dao.getDailyProgress(dateString).firstOrNull() ?: emptyList()
        val count = dailyCompleted.count { it.completed }

        var newStreak = stats.dailyStreak
        var bonusCoins = coinsReward
        var lastDate = stats.lastDailyCompletedDate

        if (count >= 10 && lastDate != dateString) {
            newStreak += 1
            bonusCoins += 100 // Bonus 100 coins for completing entire day!
            lastDate = dateString
        }

        dao.insertUserStats(
            stats.copy(
                coins = stats.coins + bonusCoins,
                dailyStreak = newStreak,
                lastDailyCompletedDate = lastDate,
                totalCorrectAnswers = stats.totalCorrectAnswers + 1
            )
        )

        checkDailyAchievements(newStreak)
    }

    suspend fun recordMathCrossSolved(boardId: Int, timeSeconds: Int, rewardCoins: Int): MathCrossSolveResult {
        return mathCrossMutex.withLock {
            val existing = dao.getMathCrossProgress(boardId)
            val alreadyClaimed = existing?.rewardClaimed == true

            val bestTime = if (existing != null && existing.bestTimeSeconds > 0) {
                minOf(existing.bestTimeSeconds, timeSeconds)
            } else {
                timeSeconds
            }

            // Always update progress as completed with best time, marking rewardClaimed as true
            dao.insertMathCrossProgress(
                MathCrossProgressEntity(
                    boardId = boardId,
                    completed = true,
                    bestTimeSeconds = bestTime,
                    rewardClaimed = true
                )
            )

            if (alreadyClaimed) {
                // Do not award coins again if already claimed!
                MathCrossSolveResult.AlreadyClaimed
            } else {
                // Award coins once on first claim
                val stats = dao.getUserStatsDirect() ?: UserStatsEntity()
                dao.insertUserStats(
                    stats.copy(
                        coins = stats.coins + rewardCoins,
                        totalCorrectAnswers = stats.totalCorrectAnswers + 1
                    )
                )
                checkCrossAchievements()
                MathCrossSolveResult.SuccessAwarded(rewardCoins)
            }
        }
    }

    suspend fun toggleSound() {
        val stats = dao.getUserStatsDirect() ?: UserStatsEntity()
        dao.insertUserStats(stats.copy(soundEnabled = !stats.soundEnabled))
    }

    suspend fun toggleHaptics() {
        val stats = dao.getUserStatsDirect() ?: UserStatsEntity()
        dao.insertUserStats(stats.copy(hapticsEnabled = !stats.hapticsEnabled))
    }

    suspend fun claimAchievement(achievementId: String) {
        val ach = dao.getAchievement(achievementId) ?: return
        if (ach.unlocked && !ach.rewardClaimed) {
            dao.updateAchievement(ach.copy(rewardClaimed = true))
            val stats = dao.getUserStatsDirect() ?: UserStatsEntity()
            dao.insertUserStats(stats.copy(coins = stats.coins + ach.coinReward))
        }
    }

    suspend fun setRemoveAds(removed: Boolean) {
        val stats = dao.getUserStatsDirect() ?: UserStatsEntity()
        dao.insertUserStats(stats.copy(hasRemovedAds = removed))
    }

    suspend fun resetAllProgress() {
        val currentStats = dao.getUserStatsDirect()
        val keepRemovedAds = currentStats?.hasRemovedAds ?: false

        dao.clearLevelProgress()
        dao.clearDailyProgress()
        dao.clearMathCrossProgress()
        dao.clearAchievements()
        dao.insertUserStats(UserStatsEntity(coins = 150, hasRemovedAds = keepRemovedAds))
        dao.insertAchievements(defaultAchievements)
    }

    private suspend fun checkLevelAchievements() {
        val levels = dao.getAllLevelProgress().firstOrNull() ?: emptyList()
        val completedCount = levels.count { it.completed }
        val totalStars = levels.sumOf { it.stars }

        updateAchievementProgress("first_step", minOf(completedCount, 1))
        updateAchievementProgress("novice_10", minOf(completedCount, 10))
        updateAchievementProgress("quarter_century", minOf(completedCount, 25))
        updateAchievementProgress("halfway_hero", minOf(completedCount, 50))
        updateAchievementProgress("centurion", minOf(completedCount, 100))
        updateAchievementProgress("star_collector_50", minOf(totalStars, 50))
        updateAchievementProgress("star_master_150", minOf(totalStars, 150))
        updateAchievementProgress("perfectionist_300", minOf(totalStars, 300))
    }

    private suspend fun checkDailyAchievements(streak: Int) {
        updateAchievementProgress("daily_starter", minOf(streak, 1))
        updateAchievementProgress("daily_streak_3", minOf(streak, 3))
        updateAchievementProgress("daily_streak_7", minOf(streak, 7))
    }

    private suspend fun checkCrossAchievements() {
        val cross = dao.getAllMathCrossProgress().firstOrNull() ?: emptyList()
        val completed = cross.count { it.completed }
        updateAchievementProgress("cross_first", minOf(completed, 1))
        updateAchievementProgress("cross_master", minOf(completed, 5))
    }

    private suspend fun updateAchievementProgress(id: String, progress: Int) {
        val ach = dao.getAchievement(id) ?: return
        val isNowUnlocked = progress >= ach.targetProgress
        dao.updateAchievement(
            ach.copy(
                currentProgress = progress,
                unlocked = isNowUnlocked || ach.unlocked
            )
        )
    }

    companion object {
        val defaultAchievements = listOf(
            AchievementEntity("first_step", "First Calculation", "Solve your very first math puzzle", "star", 0, 1, false, false, 30),
            AchievementEntity("novice_10", "Math Novice", "Complete 10 levels", "school", 0, 10, false, false, 50),
            AchievementEntity("quarter_century", "Brain Gymnast", "Complete 25 levels", "fitness_center", 0, 25, false, false, 75),
            AchievementEntity("halfway_hero", "Halfway Explorer", "Complete 50 levels", "explore", 0, 50, false, false, 100),
            AchievementEntity("centurion", "Century Grandmaster", "Conquer all 100 levels!", "military_tech", 0, 100, false, false, 250),
            AchievementEntity("star_collector_50", "Star Gazer", "Collect 50 stars across levels", "auto_awesome", 0, 50, false, false, 50),
            AchievementEntity("star_master_150", "Constellation Master", "Collect 150 stars across levels", "workspace_premium", 0, 150, false, false, 100),
            AchievementEntity("perfectionist_300", "Flawless Mind", "Collect all 300 stars!", "diamond", 0, 300, false, false, 500),
            AchievementEntity("daily_starter", "Daily Dedication", "Complete your first daily challenge", "today", 0, 1, false, false, 40),
            AchievementEntity("daily_streak_3", "Streak Seeker", "Reach a 3-day daily streak", "local_fire_department", 0, 3, false, false, 60),
            AchievementEntity("daily_streak_7", "Weekly Prodigy", "Reach a 7-day daily streak", "emoji_events", 0, 7, false, false, 150),
            AchievementEntity("cross_first", "Crossword Cadet", "Solve your first Math-Cross puzzle", "grid_view", 0, 1, false, false, 50),
            AchievementEntity("cross_master", "Math-Cross Virtuoso", "Solve 5 distinct Math-Cross boards", "pattern", 0, 5, false, false, 120)
        )
    }
}

data class LevelCompletionResult(
    val stars: Int,
    val coinsAwarded: Int,
    val isFirstCompletion: Boolean
)

sealed class MathCrossSolveResult {
    data class SuccessAwarded(val coinsAwarded: Int) : MathCrossSolveResult()
    object AlreadyClaimed : MathCrossSolveResult()
}
