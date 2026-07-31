package com.pdfpodcaster.android.data.remote.api

import com.pdfpodcaster.android.data.remote.dto.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface PipelineApi {

    @Multipart
    @POST("api/upload")
    suspend fun uploadPdf(
        @Part file: MultipartBody.Part
    ): TextResponse

    @POST("api/url")
    suspend fun fetchPdfUrl(@Body request: UrlRequest): TextResponse

    @POST("api/summarize")
    suspend fun summarize(@Body request: SummaryRequest): SummaryResponse

    @POST("api/podcast")
    suspend fun generatePodcast(@Body request: PodcastRequest): PodcastResponse
}