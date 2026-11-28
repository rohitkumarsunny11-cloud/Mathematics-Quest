package com.example.ui.screens

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import com.example.ads.AdManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import com.example.ui.theme.GlossyBlueBottom
import com.example.ui.theme.GlossyBlueTop
import com.example.ui.theme.SkyBlueDeeper
import com.example.ui.theme.SkyBlueLight
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.shadow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LevelsRepository
import com.example.data.local.LevelProgressEntity
import com.example.model.Difficulty
import com.example.model.LevelPuzzle
import com.example.ui.components.CompletionDialog
import com.example.ui.components.HeaderBar
import com.example.ui.components.HintDialog
import com.example.ui.components.MathKeypad
import com.example.ui.components.SoundAndHaptics
import com.example.ui.components.VisualPuzzleView
import com.example.ui.theme.CoralPink
import com.example.ui.theme.EasyGreen
import com.example.ui.theme.ExpertPurple
import com.example.ui.theme.HardCoral
import com.example.ui.theme.MediumAmber
import com.example.ui.theme.NormalBlue
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.SunnyYellow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PuzzlePlayScreen(
    levelId: Int,
    userCoins: Int,
    soundEnabled: Boolean,
    hapticsEnabled: Boolean,
    onBack: () -> Unit,
    onSolveLevel: (levelId: Int, timeSec: Int, hintsUsed: Int, rewardCoins: Int) -> Unit,
    onNextLevelClick: (Int) -> Unit,
    onDeductCoinsForHint: (Int) -> Boolean,
    onWrongAnswer: () -> Unit,
    onToggleSound: () -> Unit,
    onShowInterstitial: (Activity, () -> Unit) -> Unit = { _, done -> done() },
    modifier: Modifier = Modifier
) {
    val puzzle = remember(levelId) { LevelsRepository.getLevel(levelId) ?: LevelsRepository.allLevels[0] }
    val context = LocalContext.current
    val activity = context as? Activity
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    var textFieldValue by remember(levelId) { mutableStateOf(TextFieldValue("")) }
    val userInput = textFieldValue.text
    var isAnswerFocused by remember(levelId) { mutableStateOf(false) }

    var timeElapsedSeconds by remember(levelId) { mutableIntStateOf(0) }
    var isTimerRunning by remember(levelId) { mutableStateOf(true) }

    var showHintDialog by remember { mutableStateOf(false) }
    var unlockedHintTier by remember(levelId) { mutableIntStateOf(0) }
    var hintsUsedCount by remember(levelId) { mutableIntStateOf(0) }
    var isAdLoading by remember { mutableStateOf(false) }
    var adErrorMessage by remember { mutableStateOf<String?>(null) }
    var isUnlockingHint by remember { mutableStateOf(false) }

    var showCompletionDialog by remember { mutableStateOf(false) }
    var completionStars by remember { mutableIntStateOf(3) }
    var completionCoins by remember { mutableIntStateOf(puzzle.rewardCoins) }

    val shakeOffset = remember { Animatable(0f) }
    var isWrongAnswerFlash by remember { mutableStateOf(false) }

    val imeBottom = WindowInsets.ime.getBottom(density)
    val navBottom = WindowInsets.navigationBars.getBottom(density)
    val isKeyboardVisible = imeBottom > navBottom

    LaunchedEffect(isKeyboardVisible, isAnswerFocused) {
        if (isKeyboardVisible && isAnswerFocused) {
            delay(100L)
            bringIntoViewRequester.bringIntoView()
        }
    }

    // Timer
    LaunchedEffect(levelId, isTimerRunning) {
        while (isTimerRunning) {
            delay(1000L)
            timeElapsedSeconds++
        }
    }

    val diffColor = when (puzzle.difficulty) {
        Difficulty.EASY -> EasyGreen
        Difficulty.NORMAL -> NormalBlue
        Difficulty.MEDIUM -> MediumAmber
        Difficulty.HARD -> HardCoral
        Difficulty.EXPERT -> ExpertPurple
    }

    val isClock = puzzle.type == com.example.model.PuzzleType.CLOCK_MATH
    val needsTextKeyboard = remember(puzzle) {
        puzzle.type == com.example.model.PuzzleType.OPERATOR ||
            puzzle.type == com.example.model.PuzzleType.CLOCK_MATH ||
            puzzle.acceptedAnswers.any { ans -> ans.any { !it.isDigit() } }
    }
    val keyboardType = if (needsTextKeyboard) KeyboardType.Text else KeyboardType.Number

    val checkAnswer = {
        if (puzzle.isAnswerCorrect(userInput)) {
            keyboardController?.hide()
            focusManager.clearFocus()
            isTimerRunning = false
            SoundAndHaptics.playCorrect(context, soundEnabled, hapticsEnabled)

            val calculatedStars = when {
                hintsUsedCount == 0 -> 3
                hintsUsedCount == 1 -> 2
                else -> 1
            }
            completionStars = calculatedStars
            completionCoins = puzzle.rewardCoins

            onSolveLevel(puzzle.id, timeElapsedSeconds, hintsUsedCount, puzzle.rewardCoins)
            showCompletionDialog = true
        } else {
            SoundAndHaptics.playWrong(context, soundEnabled, hapticsEnabled)
            onWrongAnswer()
            isWrongAnswerFlash = true

            coroutineScope.launch {
                shakeOffset.animateTo(12f, tween(50))
                shakeOffset.animateTo(-12f, tween(50))
                shakeOffset.animateTo(8f, tween(50))
                shakeOffset.animateTo(-8f, tween(50))
                shakeOffset.animateTo(0f, tween(50))
                delay(400)
                isWrongAnswerFlash = false
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .windowInsetsPadding(WindowInsets.navigationBars.union(WindowInsets.ime))
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
        // Header
        HeaderBar(
            title = "Level ${puzzle.id}",
            onBack = {
                keyboardController?.hide()
                focusManager.clearFocus()
                onBack()
            },
            coins = userCoins,
            soundEnabled = soundEnabled,
            onToggleSound = onToggleSound
        )

        // Subheader: Difficulty chip & Timer
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = diffColor.copy(alpha = 0.2f),
                        modifier = Modifier.size(10.dp)
                    ) {}
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${puzzle.difficulty.displayName} • +${puzzle.rewardCoins} coins",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = diffColor,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = "Timer",
                        tint = GlossyBlueBottom,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "${timeElapsedSeconds}s",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E2433)
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Scrollable content area for question + visual + input
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Puzzle Question Card (Ref 1 clean white card)
            Surface(
                shape = RoundedCornerShape(26.dp),
                color = Color.White,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = puzzle.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1E2433),
                            fontSize = 18.sp
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = puzzle.question,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF5A6679),
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Visual Diagram / Representation
                    VisualPuzzleView(visual = puzzle.visual)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Reference 2: Rounded equation / answer display slot
            Box(
                modifier = Modifier
                    .offset { IntOffset(shakeOffset.value.roundToInt(), 0) }
                    .fillMaxWidth(0.85f)
                    .height(60.dp)
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .shadow(4.dp, RoundedCornerShape(30.dp))
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        if (isWrongAnswerFlash) Color(0xFFFFECEE)
                        else Color.White
                    )
                    .border(
                        width = 2.dp,
                        color = if (isWrongAnswerFlash) Color(0xFFE53935) else GlossyBlueTop,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        focusRequester.requestFocus()
                        keyboardController?.show()
                    }
                    .testTag("answer_display_slot"),
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    value = textFieldValue,
                    onValueChange = { newValue ->
                        val sanitized = newValue.text.replace("\n", "")
                        if (sanitized.length <= 10) {
                            textFieldValue = if (sanitized == newValue.text) {
                                newValue
                            } else {
                                newValue.copy(text = sanitized)
                            }
                        }
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 28.sp,
                        color = if (isWrongAnswerFlash) Color(0xFFE53935) else GlossyBlueBottom,
                        letterSpacing = 2.sp,
                        textAlign = TextAlign.Center
                    ),
                    cursorBrush = SolidColor(if (isWrongAnswerFlash) Color(0xFFE53935) else GlossyBlueBottom),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { checkAnswer() }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isAnswerFocused = focusState.isFocused
                            if (focusState.isFocused) {
                                keyboardController?.show()
                            }
                        }
                        .testTag("answer_input_field"),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (userInput.isEmpty()) {
                                Text(
                                    text = "Enter Answer",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = Color(0xFF9AA5B8),
                                        fontWeight = FontWeight.Normal,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action row: Hint Button (Ref 1 pill style)
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = Color.White,
                shadowElevation = 3.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, SunnyYellow.copy(alpha = 0.6f)),
                modifier = Modifier
                    .clip(RoundedCornerShape(22.dp))
                    .clickable {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        showHintDialog = true
                    }
                    .testTag("hint_button")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = "Get Hint",
                        tint = SunnyYellow,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (unlockedHintTier > 0) "Hints ($unlockedHintTier/3)" else "Need Hint?",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1E2433)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }


        // On-Screen Keypad (yields to native keyboard when IME is open so input field stays visible)
        if (!isKeyboardVisible) {
            MathKeypad(
                onDigitClick = { char ->
                    val next = textFieldValue.text + char
                    if (next.length <= 10) {
                        textFieldValue = TextFieldValue(next, TextRange(next.length))
                    }
                },
                onBackspace = {
                    if (textFieldValue.text.isNotEmpty()) {
                        val next = textFieldValue.text.dropLast(1)
                        textFieldValue = TextFieldValue(next, TextRange(next.length))
                    }
                },
                onClear = { textFieldValue = TextFieldValue("") },
                onSubmit = { checkAnswer() },
                canSubmit = userInput.isNotEmpty(),
                showColon = isClock,
                showMinus = true,
                soundEnabled = soundEnabled,
                hapticsEnabled = hapticsEnabled
            )
        }

        // Hint Dialog
        if (showHintDialog) {
            HintDialog(
                puzzle = puzzle,
                userCoins = userCoins,
                unlockedTier = unlockedHintTier,
                isAdLoading = isAdLoading,
                adErrorMessage = adErrorMessage,
                onWatchAdForHint = {
                    if (activity != null) {
                        isAdLoading = true
                        adErrorMessage = null
                        AdManager.loadAndShowRewardedAd(
                            activity = activity,
                            onRewardEarned = {
                                isAdLoading = false
                                unlockedHintTier = minOf(3, unlockedHintTier + 1)
                                hintsUsedCount++
                                SoundAndHaptics.playHint(context, soundEnabled, hapticsEnabled)
                            },
                            onAdFailed = { error ->
                                isAdLoading = false
                                adErrorMessage = error
                            }
                        )
                    } else {
                        adErrorMessage = "Ad player unavailable on device"
                    }
                },
                onUnlockTier = { tier, cost ->
                    if (!isUnlockingHint && unlockedHintTier < tier) {
                        isUnlockingHint = true
                        if (onDeductCoinsForHint(cost)) {
                            unlockedHintTier = maxOf(unlockedHintTier, tier)
                            hintsUsedCount++
                            SoundAndHaptics.playHint(context, soundEnabled, hapticsEnabled)
                        }
                        coroutineScope.launch {
                            delay(400L)
                            isUnlockingHint = false
                        }
                    }
                },
                onDismiss = {
                    showHintDialog = false
                    adErrorMessage = null
                    isAdLoading = false
                }
            )
        }

        // Completion Dialog
        if (showCompletionDialog) {
            val nextLevelId = LevelsRepository.getNextLevelId(puzzle.id)
            CompletionDialog(
                puzzle = puzzle,
                starsEarned = completionStars,
                coinsEarned = completionCoins,
                timeSeconds = timeElapsedSeconds,
                onNextLevel = if (nextLevelId != null) {
                    {
                        showCompletionDialog = false
                        if (activity != null) {
                            onShowInterstitial(activity) {
                                onNextLevelClick(nextLevelId)
                            }
                        } else {
                            onNextLevelClick(nextLevelId)
                        }
                    }
                } else null,
                onReplay = {
                    showCompletionDialog = false
                    textFieldValue = TextFieldValue("")
                    timeElapsedSeconds = 0
                    isTimerRunning = true
                },
                onBackToLevels = {
                    showCompletionDialog = false
                    onBack()
                }
            )
        }
    }
}
