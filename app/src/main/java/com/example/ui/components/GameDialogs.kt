package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.LevelPuzzle
import com.example.ui.theme.GoldStar
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.SunnyYellow
import com.example.ui.theme.TurquoiseAccent

import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import com.example.ui.theme.GlossyBlueBottom
import com.example.ui.theme.GlossyBlueTop
import com.example.ui.theme.RibbonBlue
import com.example.ui.theme.RibbonBlueDark
import com.example.ui.theme.SkyBlueLight

object HintPricing {
    const val TIER_1_PRICE = 200
    const val TIER_2_PRICE = 500
    const val TIER_3_PRICE = 1000

    fun getPriceForTier(tier: Int): Int = when (tier) {
        1 -> TIER_1_PRICE
        2 -> TIER_2_PRICE
        3 -> TIER_3_PRICE
        else -> 0
    }
}

@Composable
fun HintDialog(
    puzzle: LevelPuzzle,
    userCoins: Int,
    unlockedTier: Int, // 0 = none, 1 = hint1, 2 = hint2, 3 = hint3
    isAdLoading: Boolean = false,
    adErrorMessage: String? = null,
    onWatchAdForHint: () -> Unit,
    onUnlockTier: (Int, Int) -> Unit, // tier, cost
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("hint_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = SunnyYellow.copy(alpha = 0.25f),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = "Hint",
                                    tint = SunnyYellow,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Need a Hint?",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1E2433)
                            )
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // FEATURE 1: Watch Ad for Free Hint Banner
                if (unlockedTier < 3) {
                    Surface(
                        shape = RoundedCornerShape(22.dp),
                        color = Color(0xFFF3F7FF),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, GlossyBlueTop.copy(alpha = 0.35f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(22.dp))
                            .testTag("rewarded_ad_hint_banner")
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "🎬 FREE REWARDED AD OPTION",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = GlossyBlueBottom,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 0.5.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Watch a quick video ad to get 1 free hint without using your coins!",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    textAlign = TextAlign.Center,
                                    color = Color(0xFF5A6679)
                                )
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                                    .clip(RoundedCornerShape(22.dp))
                                    .background(
                                        Brush.verticalGradient(listOf(GlossyBlueTop, GlossyBlueBottom))
                                    )
                                    .clickable(enabled = !isAdLoading) { onWatchAdForHint() }
                                    .testTag("watch_ad_hint_button"),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isAdLoading) {
                                    androidx.compose.material3.CircularProgressIndicator(
                                        modifier = Modifier.size(18.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = SunnyYellow,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "WATCH AD FOR 1 FREE HINT",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = FontWeight.ExtraBold,
                                                color = Color.White,
                                                fontSize = 13.sp
                                            )
                                        )
                                    }
                                }
                            }

                            if (adErrorMessage != null) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "$adErrorMessage. You can retry or use coins below.",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.error,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "— OR UNLOCK WITH COINS —",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF8A94A6),
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Tier 1: Gentle Nudge (200 coins)
                HintTierCard(
                    tier = 1,
                    title = "Gentle Nudge",
                    content = puzzle.hint1,
                    cost = HintPricing.TIER_1_PRICE,
                    isUnlocked = unlockedTier >= 1,
                    canAfford = userCoins >= HintPricing.TIER_1_PRICE,
                    onUnlock = { onUnlockTier(1, HintPricing.TIER_1_PRICE) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Tier 2: Logical Step (500 coins)
                HintTierCard(
                    tier = 2,
                    title = "Logical Step",
                    content = puzzle.hint2,
                    cost = HintPricing.TIER_2_PRICE,
                    isUnlocked = unlockedTier >= 2,
                    canAfford = userCoins >= HintPricing.TIER_2_PRICE,
                    onUnlock = { onUnlockTier(2, HintPricing.TIER_2_PRICE) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Tier 3: Full Solution (1000 coins)
                HintTierCard(
                    tier = 3,
                    title = "Full Solution",
                    content = puzzle.hint3,
                    cost = HintPricing.TIER_3_PRICE,
                    isUnlocked = unlockedTier >= 3,
                    canAfford = userCoins >= HintPricing.TIER_3_PRICE,
                    onUnlock = { onUnlockTier(3, HintPricing.TIER_3_PRICE) }
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MonetizationOn,
                        contentDescription = "Coins",
                        tint = SunnyYellow,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Your Balance: $userCoins coins",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF5A6679)
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun HintTierCard(
    tier: Int,
    title: String,
    content: String,
    cost: Int,
    isUnlocked: Boolean,
    canAfford: Boolean,
    onUnlock: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = if (isUnlocked) TurquoiseAccent.copy(alpha = 0.12f) else Color(0xFFF7FAFD),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isUnlocked) TurquoiseAccent.copy(alpha = 0.5f) else Color(0xFFE2E8F0)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("hint_tier_$tier")
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tier $tier: $title",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E2433)
                    )
                )

                if (isUnlocked) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Unlocked",
                            tint = TurquoiseAccent,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Unlocked",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TurquoiseAccent
                            )
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .height(34.dp)
                            .clip(RoundedCornerShape(17.dp))
                            .background(if (canAfford) GlossyBlueTop else Color.LightGray)
                            .clickable(enabled = canAfford) { onUnlock() }
                            .padding(horizontal = 14.dp)
                            .testTag("unlock_hint_btn_$tier"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$cost",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            if (isUnlocked) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF2D3748),
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

@Composable
fun CompletionDialog(
    puzzle: LevelPuzzle,
    starsEarned: Int,
    coinsEarned: Int,
    timeSeconds: Int,
    onNextLevel: (() -> Unit)? = null,
    onReplay: () -> Unit,
    onBackToLevels: () -> Unit
) {
    val scale = remember { Animatable(0.4f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    Dialog(onDismissRequest = {}) {
        Card(
            shape = RoundedCornerShape(36.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .scale(scale.value)
                .padding(4.dp)
                .testTag("completion_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Radiant 3-Stars Burst (Center star larger, matching Ref 1 Screen 3)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Star 1
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star 1",
                        tint = if (starsEarned >= 1) GoldStar else Color(0xFFD6DFEC),
                        modifier = Modifier
                            .size(42.dp)
                            .testTag("star_result_1")
                    )

                    // Star 2 (Center - elevated and larger)
                    Surface(
                        shape = CircleShape,
                        color = if (starsEarned >= 2) SunnyYellow.copy(alpha = 0.25f) else Color.Transparent,
                        modifier = Modifier.size(62.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star 2",
                                tint = if (starsEarned >= 2) GoldStar else Color(0xFFD6DFEC),
                                modifier = Modifier
                                    .size(52.dp)
                                    .testTag("star_result_2")
                            )
                        }
                    }

                    // Star 3
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star 3",
                        tint = if (starsEarned >= 3) GoldStar else Color(0xFFD6DFEC),
                        modifier = Modifier
                            .size(42.dp)
                            .testTag("star_result_3")
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Blue Victory Ribbon Banner (matching Ref 1 Screen 3)
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = RibbonBlue,
                    shadowElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Great job!",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 22.sp,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "Level ${puzzle.id} complete",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Level Summary Goals Card (matching Ref 1 Screen 3 Daily Goals card)
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color(0xFFF4F7FD),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "LEVEL STATS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF7A869A),
                                letterSpacing = 1.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            // Goal 1: Coins
                            VictoryStatPill(
                                icon = Icons.Default.MonetizationOn,
                                iconTint = SunnyYellow,
                                topText = "+$coinsEarned",
                                subText = "Coins"
                            )

                            // Goal 2: Time
                            VictoryStatPill(
                                icon = Icons.Default.CheckCircle,
                                iconTint = TurquoiseAccent,
                                topText = "${timeSeconds}s",
                                subText = "Speed"
                            )

                            // Goal 3: Stars
                            VictoryStatPill(
                                icon = Icons.Default.Star,
                                iconTint = GoldStar,
                                topText = "$starsEarned/3",
                                subText = "Stars"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Mathematical Rule & Explanation Card
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFFFAFBFD),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEAEFF8)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = SunnyYellow,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Rule & Explanation",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1E2433)
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = puzzle.explanation,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF4A5568),
                                lineHeight = 18.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Glossy "NEXT LEVEL" Button (Matching Ref 1 Screen 3)
                if (onNextLevel != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .shadow(6.dp, RoundedCornerShape(26.dp))
                            .clip(RoundedCornerShape(26.dp))
                            .background(
                                Brush.verticalGradient(listOf(GlossyBlueTop, GlossyBlueBottom))
                            )
                            .clickable { onNextLevel() }
                            .testTag("next_level_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "NEXT LEVEL",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    letterSpacing = 1.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = SunnyYellow,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Glossy "HOME" and "REPLAY" buttons (Matching Ref 1 Screen 3)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFD6E3F8)),
                        shadowElevation = 2.dp,
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .clickable { onReplay() }
                            .testTag("replay_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                tint = Color(0xFF4C7BF7),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "REPLAY",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4C7BF7)
                                )
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFD6E3F8)),
                        shadowElevation = 2.dp,
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .clickable { onBackToLevels() }
                            .testTag("levels_menu_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "HOME",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4C7BF7)
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VictoryStatPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    topText: String,
    subText: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = CircleShape,
            color = iconTint.copy(alpha = 0.18f),
            modifier = Modifier.size(38.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = topText,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF1E2433)
            )
        )
        Text(
            text = subText,
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color(0xFF7A869A),
                fontSize = 11.sp
            )
        )
    }
}

