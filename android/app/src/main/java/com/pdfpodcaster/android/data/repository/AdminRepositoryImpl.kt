package com.pdfpodcaster.android.data.repository

import com.pdfpodcaster.android.data.remote.api.AdminApi
import com.pdfpodcaster.android.data.remote.dto.*
import com.pdfpodcaster.android.domain.model.*
import com.pdfpodcaster.android.domain.repository.AdminRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminRepositoryImpl @Inject constructor(
    private val adminApi: AdminApi
) : AdminRepository {

    override suspend fun getStats(): Result<AdminStats> {
        return try {
            val dto = adminApi.getStats()
            Result.success(AdminStats(
                totalUsers = dto.totalUsers,
                activeUsers = dto.activeUsers,
                totalJobs = dto.totalJobs,
                completedJobs = dto.completedJobs,
                totalGeminiUses = dto.totalGeminiUses,
                totalTokensUsedToday = dto.totalTokensUsedToday,
                remainingTokens = dto.remainingTokens
            ))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUsers(): Result<List<AdminUser>> {
        return try {
            val users = adminApi.getUsers().map { dto ->
                dto.toDomain()
            }
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createUser(
        email: String,
        password: String?,
        plan: String,
        dailyQuota: Int,
        isAdmin: Boolean
    ): Result<AdminUser> {
        return try {
            val dto = adminApi.createUser(AdminUserCreateRequest(
                email = email,
                password = password,
                plan = plan,
                dailyQuota = dailyQuota,
                isAdmin = isAdmin
            ))
            Result.success(dto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(
        userId: String,
        email: String?,
        plan: String?,
        dailyQuota: Int?,
        isAdmin: Boolean?
    ): Result<AdminUser> {
        return try {
            val dto = adminApi.updateUser(userId, AdminUserUpdateRequest(
                email = email,
                plan = plan,
                dailyQuota = dailyQuota,
                isAdmin = isAdmin
            ))
            Result.success(dto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleUserStatus(userId: String): Result<AdminUser> {
        return try {
            val dto = adminApi.toggleUserStatus(userId)
            Result.success(dto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun resetPassword(userId: String, password: String): Result<String> {
        return try {
            val response = adminApi.resetPassword(userId, AdminResetPasswordRequest(password))
            Result.success(response.message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUser(userId: String): Result<Unit> {
        return try {
            adminApi.deleteUser(userId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun bulkDeleteOldUsers(): Result<String> {
        return try {
            val response = adminApi.bulkDeleteOldUsers()
            Result.success(response.message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun AdminUserDto.toDomain() = AdminUser(
        id = id,
        email = email,
        plan = PlanType.fromValue(plan),
        dailyQuota = dailyQuota,
        usedToday = usedToday,
        tokensUsedToday = tokensUsedToday,
        totalTokensUsed = totalTokensUsed,
        isActive = isActive,
        isAdmin = isAdmin,
        createdAt = createdAt,
        totalJobs = totalJobs
    )
}