package com.example.ui.components

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SoundAndHaptics {

    private val scope = CoroutineScope(Dispatchers.Default)

    fun playClick(context: Context, soundEnabled: Boolean, hapticsEnabled: Boolean) {
        if (hapticsEnabled) triggerHapticClick(context)
        if (soundEnabled) {
            scope.launch {
                playTone(freq = 600.0, durationMs = 25)
            }
        }
    }

    fun playCorrect(context: Context, soundEnabled: Boolean, hapticsEnabled: Boolean) {
        if (hapticsEnabled) triggerHapticSuccess(context)
        if (soundEnabled) {
            scope.launch {
                playTone(freq = 523.25, durationMs = 70) // C5
                playTone(freq = 659.25, durationMs = 70) // E5
                playTone(freq = 783.99, durationMs = 120) // G5
            }
        }
    }

    fun playWrong(context: Context, soundEnabled: Boolean, hapticsEnabled: Boolean) {
        if (hapticsEnabled) triggerHapticError(context)
        if (soundEnabled) {
            scope.launch {
                playTone(freq = 220.0, durationMs = 120)
            }
        }
    }

    fun playHint(context: Context, soundEnabled: Boolean, hapticsEnabled: Boolean) {
        if (hapticsEnabled) triggerHapticClick(context)
        if (soundEnabled) {
            scope.launch {
                playTone(freq = 880.0, durationMs = 80)
                playTone(freq = 1174.66, durationMs = 100)
            }
        }
    }

    private fun triggerHapticClick(context: Context) {
        try {
            val vibrator = getVibrator(context) ?: return
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(15)
            }
        } catch (_: Exception) {}
    }

    private fun triggerHapticSuccess(context: Context) {
        try {
            val vibrator = getVibrator(context) ?: return
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val timings = longArrayOf(0, 30, 40, 60)
                val amplitudes = intArrayOf(0, 100, 0, 180)
                vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(80)
            }
        } catch (_: Exception) {}
    }

    private fun triggerHapticError(context: Context) {
        try {
            val vibrator = getVibrator(context) ?: return
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val timings = longArrayOf(0, 50, 40, 50)
                val amplitudes = intArrayOf(0, 150, 0, 150)
                vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(100)
            }
        } catch (_: Exception) {}
    }

    private fun getVibrator(context: Context): Vibrator? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    }

    private fun playTone(freq: Double, durationMs: Int) {
        try {
            val sampleRate = 22050
            val numSamples = (sampleRate * (durationMs / 1000.0)).toInt()
            val sample = ShortArray(numSamples)
            val fadeCount = minOf(numSamples / 4, 150)

            for (i in 0 until numSamples) {
                val angle = 2.0 * Math.PI * i / (sampleRate / freq)
                var amplitude = Math.sin(angle)
                // Linear fade in / fade out to eliminate popping
                if (i < fadeCount) {
                    amplitude *= (i.toDouble() / fadeCount)
                } else if (i > numSamples - fadeCount) {
                    amplitude *= ((numSamples - i).toDouble() / fadeCount)
                }
                sample[i] = (amplitude * Short.MAX_VALUE * 0.45).toInt().toShort()
            }

            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(sample.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(sample, 0, sample.size)
            audioTrack.play()
            Thread.sleep(durationMs.toLong() + 10)
            audioTrack.release()
        } catch (_: Exception) {}
    }
}
