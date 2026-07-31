package com.pdfpodcaster.android.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfpodcaster.android.domain.model.PodcastJob
import com.pdfpodcaster.android.domain.model.User
import com.pdfpodcaster.android.domain.usecase.GetCurrentUserUseCase
import com.pdfpodcaster.android.domain.usecase.LogoutUseCase
import com.pdfpodcaster.android.domain.usecase.GetJobsUseCase
import com.pdfpodcaster.android.domain.usecase.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardState(
    val user: User? = null,
    val jobs: List<PodcastJob> = emptyList(),
    val isLoading: Boolean = true,
    val isChangingPassword: Boolean = false,
    val showChangePasswordDialog: Boolean = false,
    val newPassword: String = "",
    val passwordChangeSuccess: String? = null,
    val passwordChangeError: String? = null,
    val error: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getJobsUseCase: GetJobsUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    init {
        loadUserData()
        loadJobs()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            getCurrentUserUseCase()
                .onSuccess { user ->
                    _state.value = _state.value.copy(user = user, isLoading = false)
                }
                .onFailure {
                    _state.value = _state.value.copy(isLoading = false, error = "Failed to load user data")
                }
        }
    }

    fun loadJobs() {
        viewModelScope.launch {
            getJobsUseCase()
                .onSuccess { jobs ->
                    _state.value = _state.value.copy(jobs = jobs)
                }
                .onFailure { }
        }
    }

    fun showChangePasswordDialog() {
        _state.value = _state.value.copy(
            showChangePasswordDialog = true,
            newPassword = "",
            passwordChangeSuccess = null,
            passwordChangeError = null
        )
    }

    fun hideChangePasswordDialog() {
        _state.value = _state.value.copy(showChangePasswordDialog = false)
    }

    fun onNewPasswordChange(password: String) {
        _state.value = _state.value.copy(newPassword = password, passwordChangeError = null)
    }

    fun changePassword() {
        val password = _state.value.newPassword
        if (password.length < 8) {
            _state.value = _state.value.copy(passwordChangeError = "Password must be at least 8 characters")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isChangingPassword = true)
            changePasswordUseCase(password)
                .onSuccess {
                    _state.value = _state.value.copy(
                        isChangingPassword = false,
                        passwordChangeSuccess = "Password updated successfully",
                        passwordChangeError = null
                    )
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(
                        isChangingPassword = false,
                        passwordChangeError = e.message ?: "Failed to change password"
                    )
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}