package com.pdfpodcaster.android.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdfpodcaster.android.domain.model.PipelineStep

@Composable
fun PipelineStepper(
    currentStep: PipelineStep,
    modifier: Modifier = Modifier
) {
    val steps = listOf(
        PipelineStep.EXTRACTING,
        PipelineStep.SUMMARIZING,
        PipelineStep.SCRIPTING,
        PipelineStep.AUDIO,
        PipelineStep.DONE
    )

    val stepLabels = listOf("Extract", "Summarize", "Script", "Audio", "Done")

    val currentIndex = when (currentStep) {
        PipelineStep.QUEUED -> -1
        PipelineStep.EXTRACTING -> 0
        PipelineStep.SUMMARIZING -> 1
        PipelineStep.SCRIPTING -> 2
        PipelineStep.AUDIO -> 3
        PipelineStep.DONE -> 4
        PipelineStep.FAILED -> -1
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, _ ->
            val isCompleted = currentIndex > index || currentStep == PipelineStep.DONE
            val isActive = currentIndex == index
            val isPending = currentIndex < index

            StepBadge(
                stepNumber = index + 1,
                isCompleted = isCompleted,
                isActive = isActive
            )

            if (index < steps.lastIndex) {
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .height(2.dp)
                        .background(
                            if (isCompleted) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        stepLabels.forEach { label ->
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun StepBadge(
    stepNumber: Int,
    isCompleted: Boolean,
    isActive: Boolean
) {
    val bgColor by animateColorAsState(
        targetValue = when {
            isCompleted -> MaterialTheme.colorScheme.primary
            isActive -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.surfaceVariant
        },
        label = "step_bg"
    )

    val contentColor by animateColorAsState(
        targetValue = when {
            isCompleted || isActive -> MaterialTheme.colorScheme.onPrimary
            else -> MaterialTheme.colorScheme.onSurfaceVariant
        },
        label = "step_content"
    )

    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(bgColor)
            .then(
                if (isActive) Modifier.border(
                    2.dp,
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    CircleShape
                ) else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isCompleted) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(18.dp)
            )
        } else {
            Text(
                text = stepNumber.toString(),
                color = contentColor,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}