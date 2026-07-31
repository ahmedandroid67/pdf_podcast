package com.pdfpodcaster.android.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobSubmitResponse(
    @SerialName("job_id") val jobId: String,
    val status: String
)

@Serializable
data class JobProgressDto(
    @SerialName("job_id") val jobId: String,
    val step: String,
    val status: String
)

@Serializable
data class JobDto(
    val id: String,
    @SerialName("pdf_name") val pdfName: String,
    @SerialName("pdf_size") val pdfSize: Int,
    @SerialName("summary_mode") val summaryMode: String,
    @SerialName("audio_path") val audioPath: String? = null,
    val status: String,
    @SerialName("created_at") val createdAt: String
)

@Serializable
data class JobResultDto(
    val text: String? = null,
    val summary: String? = null,
    val script: String? = null,
    @SerialName("audio_url") val audioUrl: String? = null,
    @SerialName("job_id") val jobId: String
)

@Serializable
data class UrlRequest(val url: String, val lang: String = "en", val mode: String = "podcast")