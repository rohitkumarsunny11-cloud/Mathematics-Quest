package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.LevelsRepository
import com.example.data.local.LevelProgressEntity
import com.example.model.Difficulty
import com.example.model.LevelPuzzle
import com.example.ui.components.HeaderBar
import com.example.ui.theme.EasyGreen
import com.example.ui.theme.ExpertPurple
import com.example.ui.theme.GoldStar
import com.example.ui.theme.HardCoral
import com.example.ui.theme.MediumAmber
import com.example.ui.theme.NormalBlue
import com.example.ui.theme.PurplePrimary

import androidx.compose.ui.graphics.Brush
import com.example.ui.theme.LevelCardLocked
import com.example.ui.theme.LevelCardPurple
import com.example.ui.theme.MagentaGradientBottom
import com.example.ui.theme.MagentaGradientMid
import com.example.ui.theme.MagentaGradientTop
import com.example.ui.theme.WarmGoldBg
import com.example.ui.theme.WarmGoldBgLight
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@Composable
fun LevelSelectScreen(
    levelProgressList: List<LevelProgressEntity>,
    highestUnlockedLevel: Int,
    coins: Int,
    onSelectLevel: (Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val difficulties = listOf(
        Difficulty.EASY,
        Difficulty.NORMAL,
        Difficulty.MEDIUM,
        Difficulty.HARD,
        Difficulty.EXPERT
    )

    // Determine initial tab based on highestUnlockedLevel
    val initialTab = when {
        highestUnlockedLevel <= 20 -> 0
        highestUnlockedLevel <= 40 -> 1
        highestUnlockedLevel <= 60 -> 2
        highestUnlockedLevel <= 80 -> 3
        else -> 4
    }

    var selectedTabIndex by remember { mutableIntStateOf(initialTab) }
    val currentDifficulty = difficulties[selectedTabIndex]
    val levelsInDiff = LevelsRepository.getLevelsByDifficulty(currentDifficulty)

    val progressMap = levelProgressList.associateBy { it.levelId }
    val diffCompletedCount = levelsInDiff.count { progressMap[it.id]?.completed == true }
    val diffStarsCount = levelsInDiff.sumOf { progressMap[it.id]?.stars ?: 0 }
    val totalStarsPossible = levelsInDiff.size * 3

    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(
                Brush.verticalGradient(listOf(WarmGoldBgLight, WarmGoldBg))
            )
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        // Reference 4: Large prominent rounded magenta-violet gradient container
        Surface(
            shape = RoundedCornerShape(36.dp),
            color = Color.Transparent,
            shadowElevation = 8.dp,
            border = androidx.compose.foundation.BorderStroke(3.dp, Color.White),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(MagentaGradientTop, MagentaGradientMid, MagentaGradientBottom)
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    // Reference 4 Top Bar: Circular frosted back button + "Levels" title
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0x35000000),
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .clickable { onBack() }
                                .testTag("back_button")
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        Text(
                            text = "Levels",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                fontSize = 26.sp
                            )
                        )

                        // Coins pill indicator
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0x35000000),
                            modifier = Modifier.padding(2.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = GoldStar,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "$diffStarsCount",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                            }
                        }
                    }

                    // Difficulty selector tabs
                    ScrollableTabRow(
                        selectedTabIndex = selectedTabIndex,
                        edgePadding = 4.dp,
                        containerColor = Color.Transparent,
                        divider = {},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        difficulties.forEachIndexed { index, diff ->
                            val isSelected = selectedTabIndex == index
                            Tab(
                                selected = isSelected,
                                onClick = { selectedTabIndex = index },
                                modifier = Modifier.padding(horizontal = 3.dp),
                                text = {
                                    Surface(
                                        shape = RoundedCornerShape(14.dp),
                                        color = if (isSelected) Color.White else Color(0x33FFFFFF),
                                        modifier = Modifier.clip(RoundedCornerShape(14.dp))
                                    ) {
                                        Text(
                                            text = "${diff.displayName} (${index * 20 + 1}-${index * 20 + 20})",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = FontWeight.ExtraBold,
                                                color = if (isSelected) MagentaGradientBottom else Color.White
                                            ),
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Reference 4: 3-Column Grid of Level Nodes
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("level_grid")
                    ) {
                        items(levelsInDiff) { level ->
                            val progress = progressMap[level.id]
                            val isCompleted = progress?.completed == true
                            val stars = progress?.stars ?: 0
                            val isUnlocked = level.id <= highestUnlockedLevel

                            LevelGridTile(
                                levelNumber = level.id,
                                isUnlocked = isUnlocked,
                                isCompleted = isCompleted,
                                stars = stars,
                                onClick = {
                                    if (isUnlocked) onSelectLevel(level.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LevelGridTile(
    levelNumber: Int,
    isUnlocked: Boolean,
    isCompleted: Boolean,
    stars: Int,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(22.dp),
        color = if (isUnlocked) LevelCardPurple else Color(0x30FFFFFF),
        border = if (isUnlocked) androidx.compose.foundation.BorderStroke(1.5.dp, Color(0x55FFFFFF)) else null,
        shadowElevation = if (isUnlocked) 4.dp else 0.dp,
        modifier = Modifier
            .height(84.dp)
            .clip(RoundedCornerShape(22.dp))
            .then(if (isUnlocked) Modifier.clickable { onClick() } else Modifier)
            .testTag("level_tile_$levelNumber")
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!isUnlocked) {
                // Padlock icon matching Reference 4 (level 15)
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$levelNumber",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                )
            } else {
                Text(
                    text = "$levelNumber",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 3 Mini stars underneath matching Reference 4
                Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                    for (i in 1..3) {
                        val isStarActive = i <= stars
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (isStarActive) GoldStar else Color.White.copy(alpha = 0.6f),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}

