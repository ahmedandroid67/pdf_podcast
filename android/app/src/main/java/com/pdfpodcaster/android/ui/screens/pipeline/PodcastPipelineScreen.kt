package com.pdfpodcaster.android.ui.screens.pipeline

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdfpodcaster.android.domain.model.AudioMode
import com.pdfpodcaster.android.domain.model.PipelineStep
import com.pdfpodcaster.android.domain.model.PodcastLanguage
import com.pdfpodcaster.android.ui.components.*
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastPipelineScreen(
    onBack: () -> Unit,
    onJobComplete: (jobId: String) -> Unit,
    viewModel: PodcastPipelineViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val fileName = getFileName(context, it) ?: "document.pdf"
            viewModel.onFileSelected(it, fileName)
        }
    }

    LaunchedEffect(state.status) {
        if (state.status == PipelineStatus.DONE && state.jobId != null) {
            onJobComplete(state.jobId!!)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Podcast", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state.status) {
                PipelineStatus.IDLE -> {
                    TabRow(selectedTabIndex = if (state.inputMode == InputMode.FILE) 0 else 1) {
                        Tab(
                            selected = state.inputMode == InputMode.FILE,
                            onClick = { viewModel.onInputModeChange(InputMode.FILE) },
                            text = { Text("Upload File") }
                        )
                        Tab(
                            selected = state.inputMode == InputMode.URL,
                            onClick = { viewModel.onInputModeChange(InputMode.URL) },
                            text = { Text("Paste URL") }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (state.inputMode == InputMode.FILE) {
                        DropZone(
                            onFileSelected = { filePickerLauncher.launch("application/pdf") }
                        )
                        state.selectedFileName?.let { name ->
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Selected: $name",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else {
                        OutlinedTextField(
                            value = state.url,
                            onValueChange = viewModel::onUrlChange,
                            label = { Text("PDF URL") },
                            placeholder = { Text("https://example.com/document.pdf") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    AudioModeSelector(
                        selectedMode = state.audioMode,
                        onModeSelected = viewModel::onAudioModeChange
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    PodcastLanguageSelector(
                        selectedLanguage = state.podcastLanguage,
                        onLanguageSelected = viewModel::onLanguageChange
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            if (state.inputMode == InputMode.FILE && state.selectedFileUri != null) {
                                val file = uriToFile(context, state.selectedFileUri!!)
                                viewModel.startProcessing(file)
                            } else if (state.inputMode == InputMode.URL && state.url.isNotBlank()) {
                                viewModel.startProcessing()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = (state.inputMode == InputMode.FILE && state.selectedFileUri != null) ||
                                (state.inputMode == InputMode.URL && state.url.isNotBlank()),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text("Start Processing", fontWeight = FontWeight.SemiBold)
                    }
                }

                PipelineStatus.LOADING -> {
                    Spacer(modifier = Modifier.height(32.dp))

                    PipelineStepper(currentStep = state.currentStep)

                    Spacer(modifier = Modifier.height(24.dp))

                    CircularProgressIndicator(modifier = Modifier.size(48.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        state.progressLabel,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    if (state.jobId != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Job ID: ${state.jobId}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                PipelineStatus.ERROR -> {
                    Spacer(modifier = Modifier.height(32.dp))

                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Processing Failed",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        state.error ?: "An unknown error occurred",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.reset() },
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Try Again")
                    }
                }

                PipelineStatus.DONE -> {
                    Spacer(modifier = Modifier.height(16.dp))

                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Processing Complete!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.reset() },
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Process Another")
                    }
                }
            }
        }
    }
}

private fun getFileName(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
            if (nameIndex >= 0) return it.getString(nameIndex)
        }
    }
    return uri.lastPathSegment
}

private fun uriToFile(context: Context, uri: Uri): File? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val tempFile = File.createTempFile("upload_", ".pdf", context.cacheDir)
        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        inputStream.close()
        tempFile
    } catch (e: Exception) {
        null
    }
}