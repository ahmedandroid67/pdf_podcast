package com.pdfpodcaster.android.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.sin

@Composable
fun WaveformBars(
    isPlaying: Boolean,
    barCount: Int = 10,
    color: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "waveform")

    Row(
        modifier = modifier.height(32.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        repeat(barCount) { index ->
            val animation = infiniteTransition.animateFloat(
                initialValue = 0.2f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 600 + (index * 50),
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "bar_$index"
            )

            val barHeight = if (isPlaying) {
                (animation.value * 0.6f + 0.2f)
            } else {
                0.15f + (index % 3) * 0.05f
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(barHeight)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (isPlaying) color else color.copy(alpha = 0.3f)
                    )
            )
        }
    }
}