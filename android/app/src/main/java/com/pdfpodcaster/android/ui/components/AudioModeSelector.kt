package com.pdfpodcaster.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pdfpodcaster.android.domain.model.AudioMode

@Composable
fun AudioModeSelector(
    selectedMode: AudioMode,
    onModeSelected: (AudioMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            "Audio Mode",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AudioMode.entries.forEach { mode ->
                val isSelected = mode == selectedMode
                val borderColor = if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                val bgColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                    else MaterialTheme.colorScheme.surface

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(bgColor)
                        .border(2.dp, borderColor, RoundedCornerShape(12.dp))
                        .clickable { onModeSelected(mode) }
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = when (mode) {
                            AudioMode.PODCAST -> "\uD83C\uDF99️"
                            AudioMode.DIALOGUE -> "\uD83C\uDFAD️"
                            AudioMode.AUDIOBOOK -> "\uD83D\uDCD6"
                        },
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = mode.label,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}