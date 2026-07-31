package com.pdfpodcaster.android.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdminStatsDto(
    @SerialName("total_users") val totalUsers: Int,
    @SerialName("active_users") val activeUsers: Int,
    @SerialName("total_jobs") val totalJobs: Int,
    @SerialName("completed_jobs") val completedJobs: Int,
    @SerialName("total_gemini_uses") val totalGeminiUses: Int,
    @SerialName("total_tokens_used_today") val totalTokensUsedToday: Int,
    @SerialName("remaining_tokens") val remainingTokens: Int
)

@Serializable
data class AdminUserDto(
    val id: String,
    val email: String,
    val plan: String,
    @SerialName("daily_quota") val dailyQuota: Int,
    @SerialName("used_today") val usedToday: Int,
    @SerialName("tokens_used_today") val tokensUsedToday: Int,
    @SerialName("total_tokens_used") val totalTokensUsed: Int,
    @SerialName("is_active") val isActive: Boolean,
    @SerialName("is_admin") val isAdmin: Boolean,
    @SerialName("created_at") val createdAt: String,
    @SerialName("total_jobs") val totalJobs: Int
)

@Serializable
data class AdminUserCreateRequest(
    val email: String,
    val password: String? = null,
    val plan: String = "free",
    @SerialName("daily_quota") val dailyQuota: Int = 1,
    @SerialName("is_admin") val isAdmin: Boolean = false
)

@Serializable
data class AdminUserUpdateRequest(
    val email: String? = null,
    val plan: String? = null,
    @SerialName("daily_quota") val dailyQuota: Int? = null,
    @SerialName("is_admin") val isAdmin: Boolean? = null
)

@Serializable
data class AdminResetPasswordRequest(val password: String)

@Serializable
data class AdminBulkDeleteResponse(
    val message: String
)