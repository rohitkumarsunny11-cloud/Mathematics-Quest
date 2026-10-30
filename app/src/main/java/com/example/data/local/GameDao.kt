package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    // Levels
    @Query("SELECT * FROM level_progress ORDER BY levelId ASC")
    fun getAllLevelProgress(): Flow<List<LevelProgressEntity>>

    @Query("SELECT * FROM level_progress WHERE levelId = :levelId")
    suspend fun getLevelProgress(levelId: Int): LevelProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLevelProgress(level: LevelProgressEntity)

    // User Stats
    @Query("SELECT * FROM user_stats WHERE id = 1")
    fun getUserStats(): Flow<UserStatsEntity?>

    @Query("SELECT * FROM user_stats WHERE id = 1")
    suspend fun getUserStatsDirect(): UserStatsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserStats(stats: UserStatsEntity)

    // Daily Progress
    @Query("SELECT * FROM daily_progress WHERE dateString = :dateString")
    fun getDailyProgress(dateString: String): Flow<List<DailyProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyProgress(daily: DailyProgressEntity)

    // Math Cross Progress
    @Query("SELECT * FROM math_cross_progress ORDER BY boardId ASC")
    fun getAllMathCrossProgress(): Flow<List<MathCrossProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMathCrossProgress(progress: MathCrossProgressEntity)

    // Achievements
    @Query("SELECT * FROM achievements")
    fun getAllAchievements(): Flow<List<AchievementEntity>>

    @Query("SELECT * FROM achievements WHERE id = :id")
    suspend fun getAchievement(id: String): AchievementEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievements(achievements: List<AchievementEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAchievement(achievement: AchievementEntity)

    // Reset game
    @Query("DELETE FROM level_progress")
    suspend fun clearLevelProgress()

    @Query("DELETE FROM daily_progress")
    suspend fun clearDailyProgress()

    @Query("DELETE FROM math_cross_progress")
    suspend fun clearMathCrossProgress()

    @Query("DELETE FROM achievements")
    suspend fun clearAchievements()
}
