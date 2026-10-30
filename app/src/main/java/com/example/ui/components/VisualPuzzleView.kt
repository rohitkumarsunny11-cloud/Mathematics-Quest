package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.VisualData
import com.example.ui.theme.CoralPink
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanLight
import com.example.ui.theme.PurpleLight
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.SunnyYellow
import com.example.ui.theme.TurquoiseAccent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VisualPuzzleView(
    visual: VisualData,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            when (visual) {
                is VisualData.SequenceVisual -> {
                    FlowRow(
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        visual.items.forEachIndexed { idx, item ->
                            val isUnknown = item == "?"
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = if (isUnknown) CoralPink.copy(alpha = 0.2f) else PurpleLight.copy(alpha = 0.8f),
                                border = if (isUnknown) androidx.compose.foundation.BorderStroke(2.dp, CoralPink) else null,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(54.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = item,
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp,
                                            color = if (isUnknown) CoralPink else PurplePrimary
                                        )
                                    )
                                }
                            }
                            if (idx < visual.items.size - 1) {
                                Spacer(modifier = Modifier.width(2.dp))
                            }
                        }
                    }
                }

                is VisualData.PyramidVisual -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        visual.rows.forEach { rowItems ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                rowItems.forEach { valStr ->
                                    val isUnknown = valStr == "?"
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = if (isUnknown) CoralPink.copy(alpha = 0.25f) else CyanLight,
                                        border = if (isUnknown) androidx.compose.foundation.BorderStroke(2.dp, CoralPink) else null,
                                        modifier = Modifier.size(52.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = valStr,
                                                style = MaterialTheme.typography.titleLarge.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    color = if (isUnknown) CoralPink else MaterialTheme.colorScheme.onSurface
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                is VisualData.ShapeAlgebraVisual -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        visual.equations.forEach { eq ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = eq,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                                )
                            }
                        }
                        if (visual.questionLine.isNotEmpty()) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = SunnyYellow.copy(alpha = 0.2f),
                                border = androidx.compose.foundation.BorderStroke(1.5.dp, SunnyYellow),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = visual.questionLine,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }

                is VisualData.MatrixVisual -> {
                    val tilePalette = listOf(
                        Color(0xFF00E5FF), // Cyan
                        Color(0xFF76FF03), // Lime
                        Color(0xFFFF9100), // Orange
                        Color(0xFFFFD600)  // Amber
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        visual.grid.forEachIndexed { rIdx, row ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                row.forEachIndexed { cIdx, cellText ->
                                    val isUnknown = cellText == "?"
                                    val tileColor = tilePalette[(rIdx * 2 + cIdx) % tilePalette.size]

                                    Surface(
                                        shape = RoundedCornerShape(16.dp),
                                        color = if (isUnknown) Color(0xFFF1F5FD) else tileColor,
                                        border = if (isUnknown) androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF4C7BF7)) else null,
                                        shadowElevation = if (isUnknown) 1.dp else 4.dp,
                                        modifier = Modifier.size(54.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = cellText,
                                                style = MaterialTheme.typography.titleLarge.copy(
                                                    fontWeight = FontWeight.ExtraBold,
                                                    fontSize = 22.sp,
                                                    color = if (isUnknown) Color(0xFF4C7BF7) else if (tileColor == Color(0xFF76FF03) || tileColor == Color(0xFFFFD600)) Color(0xFF1E2433) else Color.White
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                is VisualData.ClockVisual -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.Center,
                            verticalArrangement = Arrangement.Center
                        ) {
                            visual.times.forEach { t ->
                                val isUnknown = t == "?"
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = if (isUnknown) CoralPink.copy(alpha = 0.2f) else TurquoiseAccent.copy(alpha = 0.15f),
                                    border = if (isUnknown) androidx.compose.foundation.BorderStroke(2.dp, CoralPink) else null,
                                    modifier = Modifier.padding(6.dp)
                                ) {
                                    Text(
                                        text = if (isUnknown) "?" else "⏰ $t",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp,
                                            color = if (isUnknown) CoralPink else TurquoiseAccent
                                        ),
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                is VisualData.BalanceVisual -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = PurpleLight,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = visual.leftSide,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.Scale,
                                contentDescription = "Balance Scale",
                                tint = SunnyYellow,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .size(32.dp)
                            )
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = CyanLight,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = visual.rightSide,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                        if (visual.queryLine.isNotEmpty()) {
                            Text(
                                text = visual.queryLine,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = CoralPink
                                )
                            )
                        }
                    }
                }

                is VisualData.SimpleVisual -> {
                    Text(
                        text = visual.promptText,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }
    }
}
