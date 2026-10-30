package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LevelsRepository
import com.example.data.local.LevelProgressEntity
import com.example.data.local.UserStatsEntity
import com.example.ui.components.CoinBadge
import com.example.ui.components.HeaderBar
import com.example.ui.components.StarBadge
import com.example.ui.theme.BorderLight
import androidx.compose.material.icons.filled.Block
import com.example.ads.BannerAdView
import com.example.ui.theme.AmberOrange
import com.example.ui.theme.CoralPink
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.GoldStar
import com.example.ui.theme.PurpleDark
import com.example.ui.theme.PurpleLight
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.SunnyYellow
import com.example.ui.theme.TurquoiseAccent

import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import com.example.ui.theme.GlossyBlueBottom
import com.example.ui.theme.GlossyBlueTop
import com.example.ui.theme.SkyBlueCard
import com.example.ui.theme.SkyBlueDeeper
import com.example.ui.theme.SkyBlueLight

@Composable
fun HomeScreen(
    userStats: UserStatsEntity,
    levelProgressList: List<LevelProgressEntity>,
    highestUnlockedLevel: Int,
    onPlayLevel: (Int) -> Unit,
    onOpenLevelSelect: () -> Unit,
    onOpenDaily: () -> Unit,
    onOpenMathCross: () -> Unit,
    onOpenAchievements: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenSettings: () -> Unit,
    onToggleSound: () -> Unit,
    formattedRemoveAdsPrice: String = "₹99",
    onRemoveAdsClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val totalStars = levelProgressList.sumOf { it.stars }
    val completedCount = levelProgressList.count { it.completed }
    val progressFraction = (completedCount.toFloat() / LevelsRepository.TOTAL_LEVELS).coerceIn(0f, 1f)

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        SkyBlueLight,
                        SkyBlueDeeper.copy(alpha = 0.5f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        // Top Header
        HeaderBar(
            title = "Math Quest",
            coins = userStats.coins,
            stars = totalStars,
            soundEnabled = userStats.soundEnabled,
            onToggleSound = onToggleSound
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(2.dp)) }

            // Reference 1: Two floating side-by-side featured challenge cards (Daily Challenge & Quest Mode)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Daily Challenge Floating Card (Ref 1 style)
                    Card(
                        shape = RoundedCornerShape(26.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("mode_daily")
                            .clickable { onOpenDaily() }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Daily Challenge",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF1E2433)
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = SunnyYellow.copy(alpha = 0.18f),
                                modifier = Modifier.size(68.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.CalendarMonth,
                                        contentDescription = null,
                                        tint = Color(0xFFE67E22),
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (userStats.dailyStreak > 0) "${userStats.dailyStreak} Day Streak 🔥" else "10 New Puzzles",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF7A869A)
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(36.dp)
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(
                                        Brush.verticalGradient(listOf(GlossyBlueTop, GlossyBlueBottom))
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "PLAY",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White,
                                        letterSpacing = 1.sp
                                    )
                                )
                            }
                        }
                    }

                    // 100 Levels Journey Floating Card (Ref 1 style)
                    Card(
                        shape = RoundedCornerShape(26.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("mode_100_levels")
                            .clickable { onOpenLevelSelect() }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "100 Quest Levels",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF1E2433)
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = TurquoiseAccent.copy(alpha = 0.18f),
                                modifier = Modifier.size(68.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = TurquoiseAccent,
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "$completedCount / 100 Solved",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF7A869A)
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(36.dp)
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(
                                        Brush.verticalGradient(listOf(GlossyBlueTop, GlossyBlueBottom))
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "PLAY",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White,
                                        letterSpacing = 1.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Hero Main Stage Continue Card
            item {
                Card(
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("hero_continue_card")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = GlossyBlueTop.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = "CURRENT STAGE",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = GlossyBlueBottom,
                                            fontWeight = FontWeight.ExtraBold,
                                            letterSpacing = 0.5.sp
                                        ),
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Level $highestUnlockedLevel of 100",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        color = Color(0xFF1E2433),
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 20.sp
                                    )
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .shadow(4.dp, CircleShape)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.verticalGradient(listOf(SunnyYellow, AmberOrange))
                                    )
                                    .clickable { onPlayLevel(highestUnlockedLevel) }
                                    .testTag("hero_play_button"),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play Current Level",
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "$completedCount Solved",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFF7A869A),
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            Text(
                                text = "${(progressFraction * 100).toInt()}% Done",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = GlossyBlueBottom,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        LinearProgressIndicator(
                            progress = { progressFraction },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = GlossyBlueTop,
                            trackColor = Color(0xFFE2EBF8)
                        )
                    }
                }
            }

            // Reference 1: Glossy prominent "NEW GAME / CONTINUE" button
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(6.dp, RoundedCornerShape(28.dp))
                        .clip(RoundedCornerShape(28.dp))
                        .background(
                            Brush.verticalGradient(listOf(GlossyBlueTop, GlossyBlueBottom))
                        )
                        .clickable { onPlayLevel(highestUnlockedLevel) },
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (highestUnlockedLevel == 1 && completedCount == 0) "NEW GAME" else "CONTINUE GAME",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 17.sp,
                                color = Color.White,
                                letterSpacing = 1.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = SunnyYellow,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Mode Grid: Math-Cross & Achievements
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ModeCard(
                        title = "Math-Cross",
                        subtitle = "Crossword Grids",
                        badgeText = "Special",
                        icon = Icons.Default.GridOn,
                        accentColor = CyanAccent,
                        backgroundColor = CyanAccent.copy(alpha = 0.15f),
                        modifier = Modifier.weight(1f),
                        onClick = onOpenMathCross,
                        testTag = "mode_math_cross"
                    )

                    ModeCard(
                        title = "Achievements",
                        subtitle = "Earn Coin Trophies",
                        badgeText = "Trophies",
                        icon = Icons.Default.EmojiEvents,
                        accentColor = SunnyYellow,
                        backgroundColor = SunnyYellow.copy(alpha = 0.18f),
                        modifier = Modifier.weight(1f),
                        onClick = onOpenAchievements,
                        testTag = "mode_achievements"
                    )
                }
            }

            // Remove Ads Banner (If not yet purchased)
            if (!userStats.hasRemovedAds && onRemoveAdsClick != null) {
                item {
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, SunnyYellow),
                        shadowElevation = 2.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .clickable { onRemoveAdsClick() }
                            .testTag("home_remove_ads_btn")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = AmberOrange.copy(alpha = 0.18f),
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Block,
                                            contentDescription = null,
                                            tint = AmberOrange,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Remove Ads",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color(0xFF1E2433)
                                        )
                                    )
                                    Text(
                                        text = "Enjoy an ad-free puzzle journey",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = Color(0xFF7A869A)
                                        )
                                    )
                                }
                            }
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = AmberOrange
                            ) {
                                Text(
                                    text = formattedRemoveAdsPrice,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.ExtraBold
                                    ),
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }
        }

        // Reference 1: Floating Bottom Navigation Pill Bar
        Surface(
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = Color.White,
            shadowElevation = 10.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home (Active)
                IconButton(
                    onClick = { /* already home */ },
                    modifier = Modifier.testTag("nav_home")
                ) {
                    Surface(
                        shape = CircleShape,
                        color = GlossyBlueTop.copy(alpha = 0.15f),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Home",
                                tint = GlossyBlueBottom,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                // Daily Challenge
                IconButton(
                    onClick = onOpenDaily,
                    modifier = Modifier.testTag("nav_daily")
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Daily Challenge",
                        tint = Color(0xFF7A869A)
                    )
                }

                // 100 Levels Grid
                IconButton(
                    onClick = onOpenLevelSelect,
                    modifier = Modifier.testTag("nav_levels")
                ) {
                    Icon(
                        imageVector = Icons.Default.GridOn,
                        contentDescription = "Levels",
                        tint = Color(0xFF7A869A)
                    )
                }

                // Profile / Stats
                IconButton(
                    onClick = onOpenProfile,
                    modifier = Modifier.testTag("nav_profile")
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = Color(0xFF7A869A)
                    )
                }

                // Settings
                IconButton(
                    onClick = onOpenSettings,
                    modifier = Modifier.testTag("nav_settings")
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color(0xFF7A869A)
                    )
                }
            }
        }

        // Feature 2: Bottom Responsive Banner Ad
        BannerAdView(
            hasRemovedAds = userStats.hasRemovedAds,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
        )
    }
}


@Composable
private fun ModeCard(
    title: String,
    subtitle: String,
    badgeText: String,
    icon: ImageVector,
    accentColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .testTag(testTag)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = backgroundColor,
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = accentColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = badgeText,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Composable
private fun ActionNavButton(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit,
    testTag: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .testTag(testTag)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}
