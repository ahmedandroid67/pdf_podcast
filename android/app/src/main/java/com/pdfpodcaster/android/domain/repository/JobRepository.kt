package com.pdfpodcaster.android.domain.repository

import com.pdfpodcaster.android.domain.model.JobProgress
import com.pdfpodcaster.android.domain.model.JobResult
import com.pdfpodcaster.android.domain.model.PodcastJob
import java.io.File

interface JobRepository {
    suspend fun processUpload(file: File, fileName: String, lang: String, mode: String): Result<String>
    suspend fun processUrl(url: String, lang: String, mode: String): Result<String>
    suspend fun getJobs(): Result<List<PodcastJob>>
    suspend fun getJob(jobId: String): Result<PodcastJob>
    suspend fun getJobProgress(jobId: String): Result<JobProgress>
    suspend fun getJobResult(jobId: String): Result<JobResult>
}