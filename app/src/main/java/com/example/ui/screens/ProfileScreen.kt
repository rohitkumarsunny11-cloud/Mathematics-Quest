package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ui.theme.GlossyBlueBottom
import com.example.ui.theme.GlossyBlueTop
import com.example.ui.theme.SkyBlueDeeper
import com.example.ui.theme.SkyBlueLight
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LevelsRepository
import com.example.data.local.LevelProgressEntity
import com.example.data.local.UserStatsEntity
import com.example.model.Difficulty
import com.example.ui.components.HeaderBar
import com.example.ui.theme.BorderLight
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
fun ProfileScreen(
    userStats: UserStatsEntity,
    levelProgressList: List<LevelProgressEntity>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val completedCount = levelProgressList.count { it.completed }
    val totalStars = levelProgressList.sumOf { it.stars }

    val totalAttempts = userStats.totalCorrectAnswers + userStats.totalWrongAnswers
    val accuracyPercent = if (totalAttempts > 0) {
        ((userStats.totalCorrectAnswers.toFloat() / totalAttempts) * 100).toInt()
    } else 100

    // Rank title based on solved count
    val rankTitle = when {
        completedCount >= 90 -> "Grandmaster of Logic"
        completedCount >= 70 -> "Mathematical Virtuoso"
        completedCount >= 50 -> "Calculus Champion"
        completedCount >= 30 -> "Algebra Knight"
        completedCount >= 10 -> "Arithmetic Solver"
        else -> "Math Apprentice"
    }

    val progressMap = levelProgressList.associateBy { it.levelId }
    val brainScore = 100 + (completedCount * 3) + (userStats.totalCorrectAnswers * 2)


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
            title = "Profile & Statistics",
            onBack = onBack,
            coins = userStats.coins,
            stars = totalStars
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(2.dp)) }

            // Reference 3 Banner: "Train Your Brain With Our Game!"
            item {
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color.White,
                    shadowElevation = 3.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Train Your Brain With Our Game!",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1E2433),
                                fontSize = 18.sp
                            ),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Daily mental workouts & logic puzzle training",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF7A869A)
                            )
                        )
                    }
                }
            }

            // Reference 3: User Identity & Avatar Stats Hero Card
            item {
                Card(
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.fillMaxWidth().testTag("profile_hero_card")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = GlossyBlueTop.copy(alpha = 0.15f),
                                modifier = Modifier.size(64.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Psychology,
                                        contentDescription = "Avatar",
                                        tint = GlossyBlueBottom,
                                        modifier = Modifier.size(38.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column {
                                Text(
                                    text = "Brain Master",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF1E2433),
                                        fontSize = 18.sp
                                    )
                                )
                                Text(
                                    text = rankTitle,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GlossyBlueBottom
                                    )
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Level $completedCount / 100 Solved",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color(0xFF7A869A)
                                    )
                                )
                            }
                        }

                        // Right stats: Coins & Brain Score (matching Ref 3 top card)
                        Column(horizontalAlignment = Alignment.End) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "${userStats.coins}",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF1E2433)
                                    )
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.MonetizationOn,
                                    contentDescription = null,
                                    tint = SunnyYellow,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "$brainScore",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = GlossyBlueBottom
                                    )
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "🧠", fontSize = 14.sp)
                            }
                        }
                    }
                }
            }

            // 4 Metrics Grid
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        MetricCard(
                            label = "Accuracy",
                            value = "$accuracyPercent%",
                            icon = Icons.Default.CheckCircle,
                            tint = TurquoiseAccent,
                            modifier = Modifier.weight(1f)
                        )

                        MetricCard(
                            label = "Total Stars",
                            value = "$totalStars / 300",
                            icon = Icons.Default.Star,
                            tint = GoldStar,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MetricCard(
                            label = "Daily Streak",
                            value = "${userStats.dailyStreak} Days",
                            icon = Icons.Default.LocalFireDepartment,
                            tint = CoralPink,
                            modifier = Modifier.weight(1f)
                        )

                        MetricCard(
                            label = "Hints Used",
                            value = "${userStats.totalHintsUsed}",
                            icon = Icons.Default.Timeline,
                            tint = PurplePrimary,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Category Breakdown Section
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "Category Mastery",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )

                        val diffs = listOf(
                            Difficulty.EASY to EasyGreen,
                            Difficulty.NORMAL to NormalBlue,
                            Difficulty.MEDIUM to MediumAmber,
                            Difficulty.HARD to HardCoral,
                            Difficulty.EXPERT to ExpertPurple
                        )

                        diffs.forEach { (diff, color) ->
                            val levels = LevelsRepository.getLevelsByDifficulty(diff)
                            val solvedInDiff = levels.count { progressMap[it.id]?.completed == true }
                            val fraction = solvedInDiff.toFloat() / levels.size.toFloat()

                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = diff.displayName,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                                    )
                                    Text(
                                        text = "$solvedInDiff / ${levels.size}",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = color,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                LinearProgressIndicator(
                                    progress = { fraction },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    color = color,
                                    trackColor = color.copy(alpha = 0.2f)
                                )
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun MetricCard(
    label: String,
    value: String,
    icon: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = tint.copy(alpha = 0.15f),
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(22.dp))
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}
