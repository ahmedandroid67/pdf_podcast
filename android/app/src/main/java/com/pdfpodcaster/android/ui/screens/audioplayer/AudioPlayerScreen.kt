package com.pdfpodcaster.android.ui.screens.audioplayer

import android.content.Context
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdfpodcaster.android.ui.components.WaveformBars
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioPlayerScreen(
    jobId: String,
    onBack: () -> Unit,
    viewModel: AudioPlayerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(jobId) {
        viewModel.loadResult(jobId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Podcast Result", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Loading podcast...")
                    }
                }
            }
            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.ErrorOutline,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(state.error ?: "Failed to load result")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadResult(jobId) }) {
                            Text("Retry")
                        }
                    }
                }
            }
            state.result != null -> {
                val result = state.result!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WaveformBars(isPlaying = state.isPlaying)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        result.summary?.take(80)?.plus("...") ?: "Podcast",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    AudioControls(
                        isPlaying = state.isPlaying,
                        currentPosition = state.currentPosition,
                        duration = state.duration,
                        onPlayPause = viewModel::togglePlayback,
                        onSeek = viewModel::seekTo,
                        onRewind = viewModel::rewind,
                        onForward = viewModel::forward
                    )

                    Slider(
                        value = if (state.duration > 0) state.currentPosition.toFloat() / state.duration.toFloat() else 0f,
                        onValueChange = { viewModel.seekTo((it * state.duration).toLong()) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(formatTime(state.currentPosition), style = MaterialTheme.typography.bodySmall)
                        Text(formatTime(state.duration), style = MaterialTheme.typography.bodySmall)
                    }

                    result.summary?.let { summary ->
                        Spacer(modifier = Modifier.height(24.dp))
                        CollapsibleSection(title = "Summary", content = summary)
                    }

                    result.script?.let { script ->
                        Spacer(modifier = Modifier.height(16.dp))
                        CollapsibleSection(title = "Podcast Script", content = script)
                    }

                    result.text?.let { text ->
                        Spacer(modifier = Modifier.height(16.dp))
                        CollapsibleSection(title = "Original Text", content = text)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { viewModel.release() }
    }
}

@Composable
private fun AudioControls(
    isPlaying: Boolean,
    currentPosition: Long,
    duration: Long,
    onPlayPause: () -> Unit,
    onSeek: (Long) -> Unit,
    onRewind: () -> Unit,
    onForward: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onRewind, modifier = Modifier.size(48.dp)) {
            Icon(Icons.Default.Replay10, "Rewind 10s", modifier = Modifier.size(32.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        FilledIconButton(
            onClick = onPlayPause,
            modifier = Modifier.size(64.dp)
        ) {
            Icon(
                if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                if (isPlaying) "Pause" else "Play",
                modifier = Modifier.size(36.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(onClick = onForward, modifier = Modifier.size(48.dp)) {
            Icon(Icons.Default.Forward10, "Forward 10s", modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
private fun CollapsibleSection(title: String, content: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        if (expanded) "Collapse" else "Expand"
                    )
                }
            }
            AnimatedVisibility(visible = expanded) {
                Text(
                    content,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

private fun formatTime(millis: Long): String {
    val seconds = (millis / 1000).toInt()
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", minutes, secs)
}