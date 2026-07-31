package com.pdfpodcaster.android.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val email: String, val password: String)

@Serializable
data class RegisterRequest(val email: String, val password: String)

@Serializable
data class TokenResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String = "bearer"
)

@Serializable
data class UserDto(
    val id: String,
    val email: String,
    val plan: String,
    @SerialName("daily_quota") val dailyQuota: Int,
    @SerialName("used_today") val usedToday: Int,
    @SerialName("is_active") val isActive: Boolean = true,
    @SerialName("is_admin") val isAdmin: Boolean = false
)

@Serializable
data class ChangePasswordRequest(
    @SerialName("new_password") val newPassword: String
)

@Serializable
data class MessageResponse(val message: String)