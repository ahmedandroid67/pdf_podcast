package com.pdfpodcaster.android.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfpodcaster.android.domain.model.User
import com.pdfpodcaster.android.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val registerSuccess: Boolean = false
) {
    val passwordStrength: Int
        get() {
            var score = 0
            if (password.length >= 8) score++
            if (password.any { it.isUpperCase() }) score++
            if (password.any { it.isDigit() }) score++
            if (password.any { !it.isLetterOrDigit() }) score++
            return score
        }

    val passwordStrengthLabel: String
        get() = when (passwordStrength) {
            0, 1 -> "Weak"
            2 -> "Fair"
            3 -> "Strong"
            else -> "Very Strong"
        }
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email, error = null)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password, error = null)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _state.value = _state.value.copy(confirmPassword = confirmPassword, error = null)
    }

    fun register() {
        val email = _state.value.email.trim()
        val password = _state.value.password
        val confirmPassword = _state.value.confirmPassword

        if (email.isBlank()) {
            _state.value = _state.value.copy(error = "Email is required")
            return
        }
        if (password.length < 8) {
            _state.value = _state.value.copy(error = "Password must be at least 8 characters")
            return
        }
        if (password != confirmPassword) {
            _state.value = _state.value.copy(error = "Passwords do not match")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            registerUseCase(email, password)
                .onSuccess {
                    _state.value = _state.value.copy(isLoading = false, registerSuccess = true)
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "Registration failed"
                    )
                }
        }
    }
}