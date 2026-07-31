package com.pdfpodcaster.android.data.remote.api

import com.pdfpodcaster.android.data.remote.dto.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface JobApi {

    @Multipart
    @POST("api/process/upload")
    suspend fun processUpload(
        @Part file: MultipartBody.Part,
        @Part("lang") lang: String,
        @Part("mode") mode: String
    ): JobSubmitResponse

    @POST("api/process/url")
    suspend fun processUrl(@Body request: UrlRequest): JobSubmitResponse

    @GET("api/jobs")
    suspend fun getJobs(): List<JobDto>

    @GET("api/jobs/{jobId}")
    suspend fun getJob(@Path("jobId") jobId: String): JobDto

    @GET("api/jobs/{jobId}/progress")
    suspend fun getJobProgress(@Path("jobId") jobId: String): JobProgressDto

    @GET("api/jobs/{jobId}/result")
    suspend fun getJobResult(@Path("jobId") jobId: String): JobResultDto
}