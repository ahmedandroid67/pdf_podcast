package com.pdfpodcaster.android.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TextResponse(val text: String)

@Serializable
data class SummaryRequest(
    val text: String,
    val lang: String = "en",
    val mode: String = "podcast"
)

@Serializable
data class SummaryResponse(val summary: String)

@Serializable
data class PodcastRequest(
    val summary: String,
    val lang: String = "en",
    val mode: String = "podcast"
)

@Serializable
data class PodcastResponse(
    val script: String,
    @SerialName("audio_url") val audioUrl: String,
    @SerialName("job_id") val jobId: String? = null
)