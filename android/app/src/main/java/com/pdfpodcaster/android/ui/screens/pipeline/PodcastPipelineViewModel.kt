package com.pdfpodcaster.android.ui.screens.pipeline

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfpodcaster.android.domain.model.*
import com.pdfpodcaster.android.domain.usecase.ProcessUploadUseCase
import com.pdfpodcaster.android.domain.usecase.ProcessUrlUseCase
import com.pdfpodcaster.android.domain.usecase.PollJobProgressUseCase
import com.pdfpodcaster.android.domain.usecase.GetJobResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

enum class PipelineStatus { IDLE, LOADING, ERROR, DONE }
enum class InputMode { FILE, URL }

data class PipelineState(
    val status: PipelineStatus = PipelineStatus.IDLE,
    val inputMode: InputMode = InputMode.FILE,
    val selectedFileUri: Uri? = null,
    val selectedFileName: String? = null,
    val url: String = "",
    val audioMode: AudioMode = AudioMode.PODCAST,
    val podcastLanguage: PodcastLanguage = PodcastLanguage.ENGLISH,
    val currentStep: PipelineStep = PipelineStep.QUEUED,
    val jobId: String? = null,
    val progressLabel: String = "",
    val result: JobResult? = null,
    val error: String? = null
)

@HiltViewModel
class PodcastPipelineViewModel @Inject constructor(
    private val processUploadUseCase: ProcessUploadUseCase,
    private val processUrlUseCase: ProcessUrlUseCase,
    private val pollJobProgressUseCase: PollJobProgressUseCase,
    private val getJobResultUseCase: GetJobResultUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PipelineState())
    val state: StateFlow<PipelineState> = _state.asStateFlow()

    fun onInputModeChange(mode: InputMode) {
        _state.value = _state.value.copy(inputMode = mode, error = null)
    }

    fun onFileSelected(uri: Uri?, fileName: String?) {
        _state.value = _state.value.copy(
            selectedFileUri = uri,
            selectedFileName = fileName,
            error = null
        )
    }

    fun onUrlChange(url: String) {
        _state.value = _state.value.copy(url = url, error = null)
    }

    fun onAudioModeChange(mode: AudioMode) {
        _state.value = _state.value.copy(audioMode = mode)
    }

    fun onLanguageChange(lang: PodcastLanguage) {
        _state.value = _state.value.copy(podcastLanguage = lang)
    }

    fun startProcessing(file: File? = null) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                status = PipelineStatus.LOADING,
                currentStep = PipelineStep.QUEUED,
                progressLabel = "Starting...",
                error = null
            )

            val result = if (_state.value.inputMode == InputMode.FILE && file != null) {
                processUploadUseCase(
                    file = file,
                    fileName = _state.value.selectedFileName ?: "document.pdf",
                    lang = _state.value.podcastLanguage.code,
                    mode = _state.value.audioMode.value
                )
            } else {
                processUrlUseCase(
                    url = _state.value.url,
                    lang = _state.value.podcastLanguage.code,
                    mode = _state.value.audioMode.value
                )
            }

            result.onSuccess { jobId ->
                _state.value = _state.value.copy(jobId = jobId)
                startPolling(jobId)
            }.onFailure { e ->
                _state.value = _state.value.copy(
                    status = PipelineStatus.ERROR,
                    error = e.message ?: "Failed to start processing"
                )
            }
        }
    }

    private fun startPolling(jobId: String) {
        viewModelScope.launch {
            pollJobProgressUseCase.invoke(jobId).collect { progress ->
                _state.value = _state.value.copy(
                    currentStep = progress.step,
                    progressLabel = when (progress.step) {
                        PipelineStep.QUEUED -> "Queued..."
                        PipelineStep.EXTRACTING -> "Extracting text from PDF..."
                        PipelineStep.SUMMARIZING -> "Summarizing with AI..."
                        PipelineStep.SCRIPTING -> "Generating podcast script..."
                        PipelineStep.AUDIO -> "Generating audio..."
                        PipelineStep.DONE -> "Complete!"
                        PipelineStep.FAILED -> "Failed"
                    }
                )

                if (progress.step == PipelineStep.DONE) {
                    fetchResult(jobId)
                } else if (progress.step == PipelineStep.FAILED) {
                    _state.value = _state.value.copy(
                        status = PipelineStatus.ERROR,
                        error = "Processing failed"
                    )
                }
            }
        }
    }

    private suspend fun fetchResult(jobId: String) {
        getJobResultUseCase(jobId).onSuccess { result ->
            _state.value = _state.value.copy(
                status = PipelineStatus.DONE,
                result = result
            )
        }.onFailure { e ->
            _state.value = _state.value.copy(
                status = PipelineStatus.ERROR,
                error = e.message ?: "Failed to fetch result"
            )
        }
    }

    fun reset() {
        _state.value = PipelineState()
    }
}