package com.pdfpodcaster.android.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pdfpodcaster.android.domain.model.PodcastLanguage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastLanguageSelector(
    selectedLanguage: PodcastLanguage,
    onLanguageSelected: (PodcastLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            "Podcast Language",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = "${selectedLanguage.flag} ${selectedLanguage.displayName}",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                PodcastLanguage.entries.forEach { language ->
                    DropdownMenuItem(
                        text = { Text("${language.flag} ${language.displayName}") },
                        onClick = {
                            onLanguageSelected(language)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}