package com.pdfpodcaster.android.domain.repository

import com.pdfpodcaster.android.domain.model.*

interface AdminRepository {
    suspend fun getStats(): Result<AdminStats>
    suspend fun getUsers(): Result<List<AdminUser>>
    suspend fun createUser(email: String, password: String?, plan: String, dailyQuota: Int, isAdmin: Boolean): Result<AdminUser>
    suspend fun updateUser(userId: String, email: String?, plan: String?, dailyQuota: Int?, isAdmin: Boolean?): Result<AdminUser>
    suspend fun toggleUserStatus(userId: String): Result<AdminUser>
    suspend fun resetPassword(userId: String, password: String): Result<String>
    suspend fun deleteUser(userId: String): Result<Unit>
    suspend fun bulkDeleteOldUsers(): Result<String>
}