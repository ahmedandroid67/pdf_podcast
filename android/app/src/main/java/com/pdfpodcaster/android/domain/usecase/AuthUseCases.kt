package com.pdfpodcaster.android.domain.usecase

import com.pdfpodcaster.android.domain.model.User
import com.pdfpodcaster.android.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return authRepository.login(email, password)
    }
}

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return authRepository.register(email, password)
    }
}

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<User> {
        return authRepository.getMe()
    }
}

class ChangePasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(newPassword: String): Result<String> {
        return authRepository.changePassword(newPassword)
    }
}

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.logout()
    }
}

class CheckAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.isLoggedIn()
    }
}