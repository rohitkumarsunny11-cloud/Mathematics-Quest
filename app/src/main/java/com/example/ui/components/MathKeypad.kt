package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.PurpleLight
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.TurquoiseAccent

import androidx.compose.ui.graphics.Brush
import com.example.ui.theme.GlossyBlueBottom
import com.example.ui.theme.GlossyBlueTop
import com.example.ui.theme.TileCyan

@Composable
fun MathKeypad(
    onDigitClick: (String) -> Unit,
    onBackspace: () -> Unit,
    onClear: () -> Unit,
    onSubmit: () -> Unit,
    canSubmit: Boolean = true,
    showColon: Boolean = false,
    showMinus: Boolean = true,
    soundEnabled: Boolean = true,
    hapticsEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val handleKey = { char: String ->
        SoundAndHaptics.playClick(context, soundEnabled, hapticsEnabled)
        onDigitClick(char)
    }

    val handleBackspace = {
        SoundAndHaptics.playClick(context, soundEnabled, hapticsEnabled)
        onBackspace()
    }

    val handleClear = {
        SoundAndHaptics.playClick(context, soundEnabled, hapticsEnabled)
        onClear()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Row 1: 1, 2, 3, C
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            KeypadButton(text = "1", modifier = Modifier.weight(1f)) { handleKey("1") }
            KeypadButton(text = "2", modifier = Modifier.weight(1f)) { handleKey("2") }
            KeypadButton(text = "3", modifier = Modifier.weight(1f)) { handleKey("3") }
            KeypadButton(
                text = "C",
                modifier = Modifier.weight(1f),
                containerColor = Color(0xFFFFECEE),
                contentColor = Color(0xFFE53935)
            ) { handleClear() }
        }

        // Row 2: 4, 5, 6, Backspace
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            KeypadButton(text = "4", modifier = Modifier.weight(1f)) { handleKey("4") }
            KeypadButton(text = "5", modifier = Modifier.weight(1f)) { handleKey("5") }
            KeypadButton(text = "6", modifier = Modifier.weight(1f)) { handleKey("6") }
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = Color(0xFFF1F5FD),
                shadowElevation = 2.dp,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .clickable { handleBackspace() }
                    .testTag("keypad_backspace")
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Backspace,
                        contentDescription = "Backspace",
                        tint = Color(0xFF4C7BF7)
                    )
                }
            }
        }

        // Row 3: 7, 8, 9, Special (minus or colon or divide)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            KeypadButton(text = "7", modifier = Modifier.weight(1f)) { handleKey("7") }
            KeypadButton(text = "8", modifier = Modifier.weight(1f)) { handleKey("8") }
            KeypadButton(text = "9", modifier = Modifier.weight(1f)) { handleKey("9") }
            val specialKey = if (showColon) ":" else if (showMinus) "-" else "/"
            KeypadButton(
                text = specialKey,
                modifier = Modifier.weight(1f),
                containerColor = Color(0xFFF1F5FD),
                contentColor = Color(0xFF4C7BF7)
            ) { handleKey(specialKey) }
        }

        // Row 4: 0, Operator toggle, and SUBMIT
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            KeypadButton(
                text = if (showColon) "-" else "/",
                modifier = Modifier.weight(1f),
                containerColor = Color(0xFFF1F5FD),
                contentColor = Color(0xFF4C7BF7)
            ) { handleKey(if (showColon) "-" else "/") }

            KeypadButton(text = "0", modifier = Modifier.weight(1f)) { handleKey("0") }

            // Submit Button in Glossy Royal Blue or Cyan matching Ref 2
            Box(
                modifier = Modifier
                    .weight(2f)
                    .height(52.dp)
                    .shadow(4.dp, RoundedCornerShape(18.dp))
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        if (canSubmit) Brush.verticalGradient(listOf(GlossyBlueTop, GlossyBlueBottom))
                        else Brush.verticalGradient(listOf(Color(0xFFCBD5E1), Color(0xFF94A3B8)))
                    )
                    .clickable(enabled = canSubmit) {
                        SoundAndHaptics.playClick(context, soundEnabled, hapticsEnabled)
                        onSubmit()
                    }
                    .testTag("submit_button"),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "SUBMIT",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun KeypadButton(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    contentColor: Color = Color(0xFF1E2433),
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = containerColor,
        shadowElevation = 3.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE8EEF8)),
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .testTag("keypad_$text")
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    color = contentColor
                )
            )
        }
    }
}

