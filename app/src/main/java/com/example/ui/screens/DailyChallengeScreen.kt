package com.example.ui.screens

import android.app.Activity
import com.example.ads.AdManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.graphics.Brush
import com.example.ui.theme.GlossyBlueBottom
import com.example.ui.theme.GlossyBlueTop
import com.example.ui.theme.SkyBlueDeeper
import com.example.ui.theme.SkyBlueLight
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.DailyChallengeGenerator
import com.example.data.local.DailyProgressEntity
import com.example.model.DailyPuzzle
import com.example.model.Difficulty
import com.example.ui.components.HeaderBar
import com.example.ui.components.MathKeypad
import com.example.ui.components.VisualPuzzleView
import com.example.ui.theme.CoralPink
import com.example.ui.theme.EasyGreen
import com.example.ui.theme.ExpertPurple
import com.example.ui.theme.GoldStar
import com.example.ui.theme.HardCoral
import com.example.ui.theme.MediumAmber
import com.example.ui.theme.NormalBlue
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.SunnyYellow
import com.example.ui.theme.TurquoiseAccent

@Composable
fun DailyChallengeScreen(
    dateString: String = DailyChallengeGenerator.getTodayDateString(),
    dailyProgressList: List<DailyProgressEntity>,
    coins: Int,
    streak: Int,
    soundEnabled: Boolean,
    hapticsEnabled: Boolean,
    onSolveDailyPuzzle: (date: String, index: Int) -> Unit,
    onAwardBonusCoins: (Int) -> Unit = {},
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dailyPuzzles = remember(dateString) { DailyChallengeGenerator.getDailyPuzzlesForDate(dateString) }
    val completedIndices = dailyProgressList.filter { it.completed }.map { it.puzzleIndex }.toSet()
    val completedCount = completedIndices.size

    val context = androidx.compose.ui.platform.LocalContext.current
    val activity = context as? Activity

    var activePuzzleIndex by remember { mutableStateOf<Int?>(null) }
    var isBonusAdLoading by remember { mutableStateOf(false) }
    var bonusAdClaimed by remember { mutableStateOf(false) }
    var bonusAdErrorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(
                Brush.verticalGradient(
                    listOf(
                        SkyBlueLight,
                        SkyBlueDeeper.copy(alpha = 0.4f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        HeaderBar(
            title = "Daily Challenge",
            onBack = onBack,
            coins = coins
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            // Daily Banner Card
            item {
                Card(
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = CoralPink),
                    modifier = Modifier.fillMaxWidth().testTag("daily_banner_card")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = dateString,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Color.White.copy(alpha = 0.85f),
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "10 Daily Math Puzzles",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Color.White.copy(alpha = 0.25f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocalFireDepartment,
                                        contentDescription = "Streak",
                                        tint = SunnyYellow,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "$streak Streak",
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Progress: $completedCount / 10",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            if (completedCount == 10) {
                                Text(
                                    text = "All Solved! 🎉",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = SunnyYellow,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            } else {
                                Text(
                                    text = "+100 Bonus Coins at 10/10",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White.copy(alpha = 0.9f)
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        LinearProgressIndicator(
                            progress = { completedCount.toFloat() / 10f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = SunnyYellow,
                            trackColor = Color.White.copy(alpha = 0.3f)
                        )
                    }
                }
            }

            // Feature 4: Optional Rewarded Interstitial Ad (Daily Bonus Chest)
            item {
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = SunnyYellow.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, SunnyYellow),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("daily_bonus_interstitial_card")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "🎁 Daily Bonus Chest",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = SunnyYellow
                                ) {
                                    Text(
                                        text = "+50 COINS",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Watch a short rewarded sponsor video to earn 50 extra bonus coins today!",
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                            if (bonusAdErrorMessage != null) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = bonusAdErrorMessage ?: "",
                                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        if (bonusAdClaimed) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = TurquoiseAccent.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "Claimed ✓",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = TurquoiseAccent,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                )
                            }
                        } else {
                            Button(
                                onClick = {
                                    if (activity != null) {
                                        isBonusAdLoading = true
                                        bonusAdErrorMessage = null
                                        AdManager.loadAndShowRewardedInterstitial(
                                            activity = activity,
                                            onRewardEarned = {
                                                isBonusAdLoading = false
                                                bonusAdClaimed = true
                                                onAwardBonusCoins(50)
                                            },
                                            onDismissed = { earned ->
                                                isBonusAdLoading = false
                                                if (!earned && !bonusAdClaimed) {
                                                    bonusAdErrorMessage = "Reward not earned (ad skipped or cancelled)"
                                                }
                                            }
                                        )
                                    } else {
                                        bonusAdErrorMessage = "Ad player unavailable"
                                    }
                                },
                                enabled = !isBonusAdLoading,
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SunnyYellow),
                                modifier = Modifier.testTag("claim_bonus_interstitial_btn")
                            ) {
                                Text(
                                    text = if (isBonusAdLoading) "Loading..." else "CLAIM",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // 10 Puzzle List
            itemsIndexed(dailyPuzzles) { index, item ->
                val isSolved = completedIndices.contains(index)
                val diffColor = when (item.puzzle.difficulty) {
                    Difficulty.EASY -> EasyGreen
                    Difficulty.NORMAL -> NormalBlue
                    Difficulty.MEDIUM -> MediumAmber
                    Difficulty.HARD -> HardCoral
                    Difficulty.EXPERT -> ExpertPurple
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSolved) TurquoiseAccent.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = if (isSolved) TurquoiseAccent.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outline
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { activePuzzleIndex = index }
                        .testTag("daily_puzzle_tile_$index")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = if (isSolved) TurquoiseAccent else diffColor.copy(alpha = 0.2f),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    if (isSolved) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Solved",
                                            tint = Color.White,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    } else {
                                        Text(
                                            text = "${index + 1}",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = diffColor
                                            )
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column {
                                Text(
                                    text = item.puzzle.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                )
                                Text(
                                    text = "${item.puzzle.difficulty.displayName} • ${item.puzzle.type.name.replace('_', ' ')}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }

                        if (isSolved) {
                            Text(
                                text = "Solved",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = TurquoiseAccent,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        } else {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = PurplePrimary.copy(alpha = 0.12f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = PurplePrimary, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Play", style = MaterialTheme.typography.labelSmall.copy(color = PurplePrimary, fontWeight = FontWeight.Bold))
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }

        // Active Daily Puzzle Play Dialog
        val activeIdx = activePuzzleIndex
        if (activeIdx != null && activeIdx in dailyPuzzles.indices) {
            val currentDaily = dailyPuzzles[activeIdx]
            val alreadySolved = completedIndices.contains(activeIdx)

            DailyPuzzlePlayDialog(
                dailyPuzzle = currentDaily,
                alreadySolved = alreadySolved,
                soundEnabled = soundEnabled,
                hapticsEnabled = hapticsEnabled,
                onSolved = {
                    onSolveDailyPuzzle(dateString, activeIdx)
                    activePuzzleIndex = null
                },
                onDismiss = { activePuzzleIndex = null }
            )
        }
    }
}

@Composable
private fun DailyPuzzlePlayDialog(
    dailyPuzzle: DailyPuzzle,
    alreadySolved: Boolean,
    soundEnabled: Boolean,
    hapticsEnabled: Boolean,
    onSolved: () -> Unit,
    onDismiss: () -> Unit
) {
    var input by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("daily_play_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Puzzle #${dailyPuzzle.index + 1}: ${dailyPuzzle.puzzle.title}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = dailyPuzzle.puzzle.question,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                VisualPuzzleView(visual = dailyPuzzle.puzzle.visual)

                Spacer(modifier = Modifier.height(10.dp))

                // Input box
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    border = if (isError) androidx.compose.foundation.BorderStroke(2.dp, CoralPink) else null,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = if (input.isEmpty()) "Answer" else input,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isError) CoralPink else PurplePrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                MathKeypad(
                    onDigitClick = { if (input.length < 8) input += it },
                    onBackspace = { if (input.isNotEmpty()) input = input.dropLast(1) },
                    onClear = { input = "" },
                    onSubmit = {
                        if (dailyPuzzle.puzzle.isAnswerCorrect(input)) {
                            onSolved()
                        } else {
                            isError = true
                        }
                    },
                    canSubmit = input.isNotEmpty(),
                    soundEnabled = soundEnabled,
                    hapticsEnabled = hapticsEnabled
                )

                Spacer(modifier = Modifier.height(6.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Close", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
