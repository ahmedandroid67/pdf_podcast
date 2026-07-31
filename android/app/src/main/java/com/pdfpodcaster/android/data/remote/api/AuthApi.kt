package com.pdfpodcaster.android.data.remote.api

import com.pdfpodcaster.android.data.remote.dto.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface AuthApi {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): TokenResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): UserDto

    @GET("auth/me")
    suspend fun getMe(): UserDto

    @POST("auth/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): MessageResponse

    @GET("auth/google")
    suspend fun getGoogleAuthUrl(): retrofit2.Response<Unit>
}