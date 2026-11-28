package com.example

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.GameViewModel
import com.example.ui.Screen
import com.example.ui.screens.AchievementsScreen
import com.example.ui.screens.DailyChallengeScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LevelSelectScreen
import com.example.ui.screens.MathCrossScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.PuzzlePlayScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.MathQuestTheme

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MathQuestTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GameAppContent(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun GameAppContent(viewModel: GameViewModel) {
    val context = LocalContext.current
    val activity = context as? Activity

    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val userStats by viewModel.userStats.collectAsStateWithLifecycle()
    val levelProgressList by viewModel.levelProgressList.collectAsStateWithLifecycle()
    val mathCrossProgressList by viewModel.mathCrossProgressList.collectAsStateWithLifecycle()
    val achievements by viewModel.achievements.collectAsStateWithLifecycle()
    val dailyProgressList by viewModel.dailyProgressList.collectAsStateWithLifecycle()
    val formattedRemoveAdsPrice by viewModel.formattedRemoveAdsPrice.collectAsStateWithLifecycle()
    val billingStatusMessage by viewModel.billingStatusMessage.collectAsStateWithLifecycle()
    val removeAdsComingSoonMessage by viewModel.removeAdsComingSoonMessage.collectAsStateWithLifecycle()

    val highestUnlockedLevel = viewModel.getHighestUnlockedLevel()

    if (removeAdsComingSoonMessage != null) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { viewModel.dismissRemoveAdsComingSoon() },
            title = {
                androidx.compose.material3.Text(
                    text = "Coming Soon!",
                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold,
                        color = androidx.compose.ui.graphics.Color(0xFF1E2433)
                    )
                )
            },
            text = {
                androidx.compose.material3.Text(
                    text = "Remove Ads feature will be available soon.",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium.copy(
                        color = androidx.compose.ui.graphics.Color(0xFF5A6679)
                    )
                )
            },
            confirmButton = {
                androidx.compose.material3.Button(
                    onClick = { viewModel.dismissRemoveAdsComingSoon() },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = com.example.ui.theme.GlossyBlueTop
                    ),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                ) {
                    androidx.compose.material3.Text(
                        "OK",
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                }
            },
            shape = androidx.compose.foundation.shape.RoundedCornerShape(22.dp),
            containerColor = androidx.compose.ui.graphics.Color.White
        )
    }

    BackHandler(enabled = currentScreen !is Screen.Home && currentScreen !is Screen.Splash) {
        viewModel.navigateBack()
    }

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "screen_transition"
    ) { screen ->
        when (screen) {
            is Screen.Splash -> {
                SplashScreen(
                    onStartGame = { viewModel.navigateTo(Screen.Home) }
                )
            }

            is Screen.Home -> {
                HomeScreen(
                    userStats = userStats,
                    levelProgressList = levelProgressList,
                    highestUnlockedLevel = highestUnlockedLevel,
                    onPlayLevel = { lvl -> viewModel.startPlayingLevel(lvl) },
                    onOpenLevelSelect = { viewModel.navigateTo(Screen.LevelSelect) },
                    onOpenDaily = { viewModel.navigateTo(Screen.DailyChallenge) },
                    onOpenMathCross = { viewModel.navigateTo(Screen.MathCross) },
                    onOpenAchievements = { viewModel.navigateTo(Screen.Achievements) },
                    onOpenProfile = { viewModel.navigateTo(Screen.Profile) },
                    onOpenSettings = { viewModel.navigateTo(Screen.Settings) },
                    onToggleSound = { viewModel.toggleSound() },
                    formattedRemoveAdsPrice = formattedRemoveAdsPrice,
                    onRemoveAdsClick = { activity?.let { viewModel.launchRemoveAdsPurchase(it) } }
                )
            }

            is Screen.LevelSelect -> {
                LevelSelectScreen(
                    levelProgressList = levelProgressList,
                    highestUnlockedLevel = highestUnlockedLevel,
                    coins = userStats.coins,
                    onSelectLevel = { lvl -> viewModel.startPlayingLevel(lvl) },
                    onBack = { viewModel.navigateBack() }
                )
            }

            is Screen.PuzzlePlay -> {
                PuzzlePlayScreen(
                    levelId = screen.levelId,
                    userCoins = userStats.coins,
                    soundEnabled = userStats.soundEnabled,
                    hapticsEnabled = userStats.hapticsEnabled,
                    onBack = { viewModel.navigateBack() },
                    onSolveLevel = { id, time, hints, reward ->
                        viewModel.solveLevel(id, time, hints, reward)
                    },
                    onNextLevelClick = { nextId ->
                        viewModel.startPlayingLevel(nextId)
                    },
                    onDeductCoinsForHint = { cost ->
                        viewModel.deductCoinsForHint(cost)
                    },
                    onWrongAnswer = {
                        viewModel.recordWrongAnswer()
                    },
                    onToggleSound = { viewModel.toggleSound() },
                    onShowInterstitial = { act, onDone ->
                        viewModel.checkAndShowInterstitial(act, onDone)
                    }
                )
            }

            is Screen.DailyChallenge -> {
                DailyChallengeScreen(
                    dailyProgressList = dailyProgressList,
                    coins = userStats.coins,
                    streak = userStats.dailyStreak,
                    soundEnabled = userStats.soundEnabled,
                    hapticsEnabled = userStats.hapticsEnabled,
                    onSolveDailyPuzzle = { date, idx ->
                        viewModel.solveDaily(date, idx)
                    },
                    onAwardBonusCoins = { coins ->
                        viewModel.awardBonusDailyCoins(coins)
                    },
                    onBack = { viewModel.navigateBack() }
                )
            }

            is Screen.MathCross -> {
                MathCrossScreen(
                    coins = userStats.coins,
                    soundEnabled = userStats.soundEnabled,
                    hapticsEnabled = userStats.hapticsEnabled,
                    progressList = mathCrossProgressList,
                    onSolveBoard = { boardId, time, reward, onResult ->
                        viewModel.solveMathCross(boardId, time, reward, onResult)
                    },
                    onBack = { viewModel.navigateBack() }
                )
            }

            is Screen.Profile -> {
                ProfileScreen(
                    userStats = userStats,
                    levelProgressList = levelProgressList,
                    onBack = { viewModel.navigateBack() }
                )
            }

            is Screen.Achievements -> {
                AchievementsScreen(
                    achievements = achievements,
                    coins = userStats.coins,
                    onClaimReward = { achId -> viewModel.claimAchievement(achId) },
                    onBack = { viewModel.navigateBack() }
                )
            }

            is Screen.Settings -> {
                SettingsScreen(
                    userStats = userStats,
                    onToggleSound = { viewModel.toggleSound() },
                    onToggleHaptics = { viewModel.toggleHaptics() },
                    onResetProgress = { viewModel.resetAllProgress() },
                    onBack = { viewModel.navigateBack() },
                    formattedPrice = formattedRemoveAdsPrice,
                    billingStatusMessage = billingStatusMessage,
                    onRemoveAdsClick = { activity?.let { viewModel.launchRemoveAdsPurchase(it) } },
                    onRestorePurchasesClick = { viewModel.restorePurchases() },
                    onClearBillingMessage = { viewModel.clearBillingMessage() }
                )
            }
        }
    }
}
