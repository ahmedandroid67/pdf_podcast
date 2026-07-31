package com.pdfpodcaster.android.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfpodcaster.android.domain.model.User
import com.pdfpodcaster.android.domain.usecase.LoginUseCase
import com.pdfpodcaster.android.domain.usecase.CheckAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val loginSuccess: Boolean = false,
    val isCheckingAuth: Boolean = true
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkAuthUseCase: CheckAuthUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    init {
        checkExistingAuth()
    }

    private fun checkExistingAuth() {
        viewModelScope.launch {
            val isLoggedIn = checkAuthUseCase()
            if (isLoggedIn) {
                _state.value = _state.value.copy(
                    loginSuccess = true,
                    isCheckingAuth = false
                )
            } else {
                _state.value = _state.value.copy(isCheckingAuth = false)
            }
        }
    }

    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email, error = null)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password, error = null)
    }

    fun login() {
        val email = _state.value.email.trim()
        val password = _state.value.password

        if (email.isBlank()) {
            _state.value = _state.value.copy(error = "Email is required")
            return
        }
        if (password.length < 8) {
            _state.value = _state.value.copy(error = "Password must be at least 8 characters")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            loginUseCase(email, password)
                .onSuccess {
                    _state.value = _state.value.copy(isLoading = false, loginSuccess = true)
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "Login failed"
                    )
                }
        }
    }
}