package com.example.ui

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ads.AdManager
import com.example.billing.BillingManager
import com.example.data.DailyChallengeGenerator
import com.example.data.GameRepository
import com.example.data.local.AchievementEntity
import com.example.data.local.DailyProgressEntity
import com.example.data.local.GameDatabase
import com.example.data.local.LevelProgressEntity
import com.example.data.local.MathCrossProgressEntity
import com.example.data.local.UserStatsEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Screen {
    object Splash : Screen()
    object Home : Screen()
    object LevelSelect : Screen()
    data class PuzzlePlay(val levelId: Int) : Screen()
    object DailyChallenge : Screen()
    object MathCross : Screen()
    object Profile : Screen()
    object Achievements : Screen()
    object Settings : Screen()
}

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository
    val billingManager: BillingManager

    val userStats: StateFlow<UserStatsEntity>
    val levelProgressList: StateFlow<List<LevelProgressEntity>>
    val mathCrossProgressList: StateFlow<List<MathCrossProgressEntity>>
    val achievements: StateFlow<List<AchievementEntity>>
    val dailyProgressList: StateFlow<List<DailyProgressEntity>>

    private val _currentScreen = MutableStateFlow<Screen>(Screen.Splash)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _screenStack = mutableListOf<Screen>()

    val formattedRemoveAdsPrice: StateFlow<String> get() = billingManager.formattedPrice
    val billingStatusMessage: StateFlow<String?> get() = billingManager.purchaseStatusMessage

    fun clearBillingMessage() {
        billingManager.clearStatusMessage()
    }

    // Track completed levels in this session for interstitial frequency control
    var sessionCompletedLevelsCount: Int = 0
        private set

    init {
        val db = GameDatabase.getDatabase(application)
        repository = GameRepository(db.gameDao())

        // Initialize Google Mobile Ads SDK safely
        AdManager.initialize(application)

        // Initialize Google Play Billing Client
        billingManager = BillingManager(application) { hasRemovedAds ->
            setRemoveAds(hasRemovedAds)
        }

        userStats = repository.userStats.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            UserStatsEntity()
        )

        levelProgressList = repository.levelProgressList.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

        mathCrossProgressList = repository.mathCrossProgressList.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

        achievements = repository.achievements.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

        val today = DailyChallengeGenerator.getTodayDateString()
        dailyProgressList = repository.getDailyProgressForDate(today).stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

        viewModelScope.launch {
            repository.initializeIfNeeded()
        }
    }

    fun navigateTo(screen: Screen) {
        _screenStack.add(_currentScreen.value)
        _currentScreen.value = screen
    }

    fun navigateBack(): Boolean {
        return if (_screenStack.isNotEmpty()) {
            val prev = _screenStack.removeAt(_screenStack.size - 1)
            _currentScreen.value = prev
            true
        } else if (_currentScreen.value !is Screen.Home) {
            _currentScreen.value = Screen.Home
            true
        } else {
            false
        }
    }

    fun startPlayingLevel(levelId: Int) {
        navigateTo(Screen.PuzzlePlay(levelId))
    }

    fun solveLevel(levelId: Int, timeSec: Int, hintsUsed: Int, rewardCoins: Int) {
        sessionCompletedLevelsCount++
        viewModelScope.launch {
            repository.recordLevelCompletion(levelId, timeSec, hintsUsed, rewardCoins)
        }
    }

    fun checkAndShowInterstitial(activity: Activity, onComplete: () -> Unit) {
        val hasRemoved = userStats.value.hasRemovedAds
        AdManager.showInterstitialIfEligible(
            activity = activity,
            sessionCompletedLevels = sessionCompletedLevelsCount,
            hasRemovedAds = hasRemoved,
            onComplete = onComplete
        )
    }

    fun deductCoinsForHint(cost: Int): Boolean {
        var success = false
        val currentCoins = userStats.value.coins
        if (currentCoins >= cost) {
            success = true
            viewModelScope.launch {
                repository.deductCoinsForHint(cost)
            }
        }
        return success
    }

    fun recordWrongAnswer() {
        viewModelScope.launch {
            repository.recordWrongAnswer()
        }
    }

    fun solveDaily(date: String, index: Int) {
        viewModelScope.launch {
            repository.recordDailyPuzzleSolved(date, index)
        }
    }

    fun awardBonusDailyCoins(amount: Int = 50) {
        viewModelScope.launch {
            val stats = userStats.value
            val db = GameDatabase.getDatabase(getApplication())
            db.gameDao().insertUserStats(stats.copy(coins = stats.coins + amount))
        }
    }

    fun solveMathCross(
        boardId: Int,
        timeSec: Int,
        rewardCoins: Int,
        onResult: (com.example.data.MathCrossSolveResult) -> Unit = {}
    ) {
        viewModelScope.launch {
            val result = repository.recordMathCrossSolved(boardId, timeSec, rewardCoins)
            onResult(result)
        }
    }

    fun toggleSound() {
        viewModelScope.launch {
            repository.toggleSound()
        }
    }

    fun toggleHaptics() {
        viewModelScope.launch {
            repository.toggleHaptics()
        }
    }

    fun setRemoveAds(removed: Boolean) {
        viewModelScope.launch {
            repository.setRemoveAds(removed)
        }
    }

    private val _removeAdsComingSoonMessage = MutableStateFlow<String?>(null)
    val removeAdsComingSoonMessage: StateFlow<String?> = _removeAdsComingSoonMessage.asStateFlow()

    fun showRemoveAdsComingSoon(activity: Activity? = null) {
        val msg = "Coming Soon! Remove Ads feature will be available soon."
        _removeAdsComingSoonMessage.value = msg
        billingManager.setTemporaryMessage(msg)
        activity?.let {
            android.widget.Toast.makeText(it, msg, android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    fun dismissRemoveAdsComingSoon() {
        _removeAdsComingSoonMessage.value = null
    }

    fun launchRemoveAdsPurchase(activity: Activity) {
        // Temporarily disabled Google Play Billing purchase flow as requested.
        // BillingManager code is preserved for future Google Play Console integration.
        showRemoveAdsComingSoon(activity)
    }

    fun restorePurchases(onResult: ((Boolean) -> Unit)? = null) {
        billingManager.restorePurchases(onResult)
    }

    fun claimAchievement(id: String) {
        viewModelScope.launch {
            repository.claimAchievement(id)
        }
    }

    fun resetAllProgress() {
        sessionCompletedLevelsCount = 0
        viewModelScope.launch {
            repository.resetAllProgress()
            _screenStack.clear()
            _currentScreen.value = Screen.Home
        }
    }

    fun getHighestUnlockedLevel(): Int {
        val completed = levelProgressList.value.filter { it.completed }.map { it.levelId }
        val maxId = completed.maxOrNull() ?: 0
        return maxId + 1
    }

    override fun onCleared() {
        super.onCleared()
        billingManager.destroy()
    }
}
