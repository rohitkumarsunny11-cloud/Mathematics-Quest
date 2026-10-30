package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MathCrossData
import com.example.data.local.MathCrossProgressEntity
import com.example.model.CrossCellType
import com.example.model.Difficulty
import com.example.ui.components.HeaderBar
import com.example.ui.components.MathKeypad
import com.example.ui.components.SoundAndHaptics
import com.example.ui.theme.AmberOrange
import com.example.ui.theme.CoralPink
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EasyGreen
import com.example.ui.theme.ExpertPurple
import com.example.ui.theme.GoldStar
import com.example.ui.theme.HardCoral
import com.example.ui.theme.MediumAmber
import com.example.ui.theme.NormalBlue
import com.example.ui.theme.PurpleLight
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.SunnyYellow
import com.example.ui.theme.TurquoiseAccent

@Composable
fun MathCrossScreen(
    coins: Int,
    soundEnabled: Boolean,
    hapticsEnabled: Boolean,
    progressList: List<MathCrossProgressEntity>,
    onSolveBoard: (boardId: Int, timeSec: Int, rewardCoins: Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedBoardId by remember { mutableIntStateOf(1) }
    val board = remember(selectedBoardId) { MathCrossData.getBoard(selectedBoardId) ?: MathCrossData.boards[0] }

    val userInputs = remember(selectedBoardId) { mutableStateMapOf<Pair<Int, Int>, String>() }
    var selectedCellPos by remember(selectedBoardId) {
        // default select first editable cell
        val firstEditable = board.cells.firstOrNull { it.isEditable }
        mutableStateOf(firstEditable?.let { Pair(it.row, it.col) })
    }

    var isSolvedBoard by remember(selectedBoardId) { mutableStateOf(false) }
    val context = LocalContext.current

    val solvedIds = progressList.filter { it.completed }.map { it.boardId }.toSet()

    val checkValidation = {
        if (board.isSolved(userInputs)) {
            isSolvedBoard = true
            SoundAndHaptics.playCorrect(context, soundEnabled, hapticsEnabled)
            onSolveBoard(board.id, 45, board.rewardCoins)
        }
    }

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
            title = "Math-Cross",
            onBack = onBack,
            coins = coins
        )

        // Board Selector Tabs
        ScrollableTabRow(
            selectedTabIndex = selectedBoardId - 1,
            edgePadding = 16.dp,
            containerColor = Color.Transparent,
            divider = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            MathCrossData.boards.forEach { b ->
                val isSelected = b.id == selectedBoardId
                val isBoardDone = solvedIds.contains(b.id)

                Tab(
                    selected = isSelected,
                    onClick = {
                        selectedBoardId = b.id
                        isSolvedBoard = isBoardDone
                    },
                    text = {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSelected) GlossyBlueTop else Color.White,
                            shadowElevation = if (isSelected) 3.dp else 1.dp,
                            modifier = Modifier.clip(RoundedCornerShape(16.dp))
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                            ) {
                                if (isBoardDone) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = if (isSelected) Color.White else TurquoiseAccent,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                Text(
                                    text = "Grid #${b.id}",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = if (isSelected) Color.White else Color(0xFF1E2433)
                                    )
                                )
                            }
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Instructions
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${board.title} (${board.difficulty.displayName})",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1E2433)
                            )
                        )
                        Text(
                            text = "Reward: +${board.rewardCoins} coins",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = AmberOrange,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Fill missing numbers so BOTH rows and columns are mathematically correct!",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF5A6679))
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 5x5 Crossword Grid Display matching Ref 1 Screen 2
            Card(
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .testTag("math_cross_board")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (r in 0 until board.rows) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            for (c in 0 until board.cols) {
                                val cell = board.getCell(r, c)
                                if (cell == null || cell.type == CrossCellType.EMPTY) {
                                    Box(modifier = Modifier.size(50.dp))
                                } else {
                                    val isSelected = selectedCellPos == Pair(r, c)
                                    val cellText = if (cell.isEditable) {
                                        userInputs[Pair(r, c)] ?: ""
                                    } else {
                                        cell.initialText
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(14.dp),
                                        color = when {
                                            cell.isEditable && isSelected -> SkyBlueLight
                                            cell.isEditable -> SunnyYellow.copy(alpha = 0.22f)
                                            cell.type == CrossCellType.OPERATOR -> Color(0xFFEDF2F9)
                                            cell.type == CrossCellType.EQUALS -> Color(0xFFEDF2F9)
                                            else -> Color(0xFFEDF2F9)
                                        },
                                        border = when {
                                            cell.isEditable && isSelected -> androidx.compose.foundation.BorderStroke(2.dp, GlossyBlueTop)
                                            cell.isEditable -> androidx.compose.foundation.BorderStroke(1.5.dp, SunnyYellow)
                                            else -> androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDDE5F2))
                                        },
                                        shadowElevation = if (isSelected || cell.isEditable) 2.dp else 0.dp,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(RoundedCornerShape(14.dp))
                                            .then(
                                                if (cell.isEditable) {
                                                    Modifier.clickable { selectedCellPos = Pair(r, c) }
                                                } else Modifier
                                            )
                                            .testTag("cross_cell_${r}_${c}")
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = cellText,
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontWeight = FontWeight.ExtraBold,
                                                    fontSize = if (cell.type == CrossCellType.OPERATOR) 20.sp else 18.sp,
                                                    color = when {
                                                        cell.isEditable -> GlossyBlueBottom
                                                        cell.type == CrossCellType.OPERATOR -> Color(0xFF4C7BF7)
                                                        else -> Color(0xFF1E2433)
                                                    }
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }


            // Solved notification banner
            if (isSolvedBoard) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = TurquoiseAccent.copy(alpha = 0.15f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = TurquoiseAccent)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Grid Complete! All rows and columns satisfied! +${board.rewardCoins} coins",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TurquoiseAccent
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        // Keypad for cell entry
        MathKeypad(
            onDigitClick = { char ->
                val pos = selectedCellPos
                if (pos != null) {
                    val current = userInputs[pos] ?: ""
                    if (current.length < 3) {
                        userInputs[pos] = current + char
                        checkValidation()
                    }
                }
            },
            onBackspace = {
                val pos = selectedCellPos
                if (pos != null) {
                    val current = userInputs[pos] ?: ""
                    if (current.isNotEmpty()) {
                        userInputs[pos] = current.dropLast(1)
                    }
                }
            },
            onClear = {
                val pos = selectedCellPos
                if (pos != null) {
                    userInputs.remove(pos)
                }
            },
            onSubmit = { checkValidation() },
            canSubmit = true,
            soundEnabled = soundEnabled,
            hapticsEnabled = hapticsEnabled
        )
    }
}
