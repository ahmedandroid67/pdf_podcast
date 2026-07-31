package com.pdfpodcaster.android.domain.model

enum class JobStatus {
    QUEUED,
    PROCESSING,
    COMPLETED,
    FAILED
}

enum class PipelineStep(val label: String) {
    QUEUED("Queued"),
    EXTRACTING("Extracting text"),
    SUMMARIZING("Summarizing"),
    SCRIPTING("Generating script"),
    AUDIO("Generating audio"),
    DONE("Complete"),
    FAILED("Failed");

    companion object {
        fun fromValue(value: String): PipelineStep = when (value.lowercase()) {
            "queued" -> QUEUED
            "extracting" -> EXTRACTING
            "summarizing" -> SUMMARIZING
            "scripting" -> SCRIPTING
            "audio" -> AUDIO
            "done" -> DONE
            "failed" -> FAILED
            else -> QUEUED
        }
    }
}

data class PodcastJob(
    val id: String,
    val pdfName: String,
    val pdfSize: Int,
    val summaryMode: String,
    val audioPath: String?,
    val status: JobStatus,
    val createdAt: String
)

data class JobProgress(
    val jobId: String,
    val step: PipelineStep,
    val status: String
)

data class JobResult(
    val text: String?,
    val summary: String?,
    val script: String?,
    val audioUrl: String?,
    val jobId: String
)