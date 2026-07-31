package com.pdfpodcaster.android.domain.usecase

import com.pdfpodcaster.android.domain.model.AdminStats
import com.pdfpodcaster.android.domain.model.AdminUser
import com.pdfpodcaster.android.domain.repository.AdminRepository
import javax.inject.Inject

class GetAdminStatsUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(): Result<AdminStats> = adminRepository.getStats()
}

class GetAdminUsersUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(): Result<List<AdminUser>> = adminRepository.getUsers()
}

class CreateAdminUserUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String?,
        plan: String,
        dailyQuota: Int,
        isAdmin: Boolean
    ): Result<AdminUser> = adminRepository.createUser(email, password, plan, dailyQuota, isAdmin)
}

class UpdateAdminUserUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(
        userId: String,
        email: String?,
        plan: String?,
        dailyQuota: Int?,
        isAdmin: Boolean?
    ): Result<AdminUser> = adminRepository.updateUser(userId, email, plan, dailyQuota, isAdmin)
}

class ToggleAdminUserStatusUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(userId: String): Result<AdminUser> = adminRepository.toggleUserStatus(userId)
}

class ResetAdminPasswordUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(userId: String, password: String): Result<String> = adminRepository.resetPassword(userId, password)
}

class DeleteAdminUserUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(userId: String): Result<Unit> = adminRepository.deleteUser(userId)
}

class BulkDeleteOldUsersUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(): Result<String> = adminRepository.bulkDeleteOldUsers()
}