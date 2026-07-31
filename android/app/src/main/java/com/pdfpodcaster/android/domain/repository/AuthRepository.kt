package com.pdfpodcaster.android.domain.repository

import com.pdfpodcaster.android.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String): Result<User>
    suspend fun getMe(): Result<User>
    suspend fun changePassword(newPassword: String): Result<String>
    suspend fun logout()
    suspend fun isLoggedIn(): Boolean
    suspend fun getSavedUser(): User?
}