package com.pdfpodcaster.android.ui.screens.jobhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfpodcaster.android.domain.model.PodcastJob
import com.pdfpodcaster.android.domain.model.JobStatus
import com.pdfpodcaster.android.domain.usecase.GetJobsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class JobHistoryState(
    val jobs: List<PodcastJob> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val isRefreshing: Boolean = false
)

@HiltViewModel
class JobHistoryViewModel @Inject constructor(
    private val getJobsUseCase: GetJobsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(JobHistoryState())
    val state: StateFlow<JobHistoryState> = _state.asStateFlow()

    init {
        loadJobs()
    }

    fun loadJobs() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            getJobsUseCase()
                .onSuccess { jobs ->
                    val sorted = jobs.sortedByDescending { it.createdAt }
                    _state.value = _state.value.copy(
                        jobs = sorted,
                        isLoading = false,
                        isRefreshing = false
                    )
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = e.message ?: "Failed to load jobs"
                    )
                }
        }
    }

    fun refresh() {
        _state.value = _state.value.copy(isRefreshing = true)
        loadJobs()
    }
}