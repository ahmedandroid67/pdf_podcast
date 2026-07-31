package com.pdfpodcaster.android.data.remote.api

import com.pdfpodcaster.android.data.remote.dto.*
import retrofit2.http.*

interface AdminApi {

    @GET("api/admin/stats")
    suspend fun getStats(): AdminStatsDto

    @GET("api/admin/users")
    suspend fun getUsers(): List<AdminUserDto>

    @POST("api/admin/users")
    suspend fun createUser(@Body request: AdminUserCreateRequest): AdminUserDto

    @PUT("api/admin/users/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: String,
        @Body request: AdminUserUpdateRequest
    ): AdminUserDto

    @PUT("api/admin/users/{userId}/status")
    suspend fun toggleUserStatus(@Path("userId") userId: String): AdminUserDto

    @POST("api/admin/users/{userId}/reset-password")
    suspend fun resetPassword(
        @Path("userId") userId: String,
        @Body request: AdminResetPasswordRequest
    ): MessageResponse

    @DELETE("api/admin/users/{userId}")
    suspend fun deleteUser(@Path("userId") userId: String)

    @DELETE("api/admin/users/bulk/old")
    suspend fun bulkDeleteOldUsers(): AdminBulkDeleteResponse
}