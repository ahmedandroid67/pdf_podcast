package com.pdfpodcaster.android.data.repository

import com.pdfpodcaster.android.data.local.TokenDataStore
import com.pdfpodcaster.android.data.remote.api.AuthApi
import com.pdfpodcaster.android.data.remote.dto.LoginRequest
import com.pdfpodcaster.android.data.remote.dto.RegisterRequest
import com.pdfpodcaster.android.data.remote.dto.ChangePasswordRequest
import com.pdfpodcaster.android.domain.model.PlanType
import com.pdfpodcaster.android.domain.model.User
import com.pdfpodcaster.android.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenDataStore: TokenDataStore
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val tokenResponse = authApi.login(LoginRequest(email, password))
            tokenDataStore.saveToken(tokenResponse.accessToken)
            val userDto = authApi.getMe()
            val user = User(
                id = userDto.id,
                email = userDto.email,
                plan = PlanType.fromValue(userDto.plan),
                dailyQuota = userDto.dailyQuota,
                usedToday = userDto.usedToday,
                isActive = userDto.isActive,
                isAdmin = userDto.isAdmin
            )
            tokenDataStore.saveUserEmail(user.email)
            tokenDataStore.saveUserPlan(user.plan.name)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String): Result<User> {
        return try {
            val userDto = authApi.register(RegisterRequest(email, password))
            val loginResult = login(email, password)
            loginResult
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMe(): Result<User> {
        return try {
            val userDto = authApi.getMe()
            val user = User(
                id = userDto.id,
                email = userDto.email,
                plan = PlanType.fromValue(userDto.plan),
                dailyQuota = userDto.dailyQuota,
                usedToday = userDto.usedToday,
                isActive = userDto.isActive,
                isAdmin = userDto.isAdmin
            )
            tokenDataStore.saveUserEmail(user.email)
            tokenDataStore.saveUserPlan(user.plan.name)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changePassword(newPassword: String): Result<String> {
        return try {
            val response = authApi.changePassword(ChangePasswordRequest(newPassword))
            Result.success(response.message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        tokenDataStore.clearAll()
    }

    override suspend fun isLoggedIn(): Boolean {
        return !tokenDataStore.getToken().isNullOrBlank()
    }

    override suspend fun getSavedUser(): User? {
        val email = tokenDataStore.getUserEmail() ?: return null
        val planStr = tokenDataStore.getUserPlan() ?: "FREE"
        return User(
            id = "",
            email = email,
            plan = PlanType.fromValue(planStr),
            dailyQuota = 1,
            usedToday = 0,
            isActive = true,
            isAdmin = false
        )
    }
}