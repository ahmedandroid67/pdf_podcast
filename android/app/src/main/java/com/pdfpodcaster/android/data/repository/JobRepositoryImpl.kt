package com.pdfpodcaster.android.data.repository

import com.pdfpodcaster.android.data.remote.api.JobApi
import com.pdfpodcaster.android.data.remote.dto.UrlRequest
import com.pdfpodcaster.android.domain.model.*
import com.pdfpodcaster.android.domain.repository.JobRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobRepositoryImpl @Inject constructor(
    private val jobApi: JobApi
) : JobRepository {

    override suspend fun processUpload(
        file: File,
        fileName: String,
        lang: String,
        mode: String
    ): Result<String> {
        return try {
            val requestBody = file.asRequestBody("application/pdf".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Part.createFormData("file", fileName, requestBody)
            val langBody = lang.toRequestBody("text/plain".toMediaTypeOrNull())
            val modeBody = mode.toRequestBody("text/plain".toMediaTypeOrNull())
            val response = jobApi.processUpload(multipartBody, lang, mode)
            Result.success(response.jobId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun processUrl(url: String, lang: String, mode: String): Result<String> {
        return try {
            val response = jobApi.processUrl(UrlRequest(url, lang, mode))
            Result.success(response.jobId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getJobs(): Result<List<PodcastJob>> {
        return try {
            val jobs = jobApi.getJobs().map { dto ->
                PodcastJob(
                    id = dto.id,
                    pdfName = dto.pdfName,
                    pdfSize = dto.pdfSize,
                    summaryMode = dto.summaryMode,
                    audioPath = dto.audioPath,
                    status = when (dto.status) {
                        "queued" -> JobStatus.QUEUED
                        "processing" -> JobStatus.PROCESSING
                        "completed" -> JobStatus.COMPLETED
                        "failed" -> JobStatus.FAILED
                        else -> JobStatus.QUEUED
                    },
                    createdAt = dto.createdAt
                )
            }
            Result.success(jobs)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getJob(jobId: String): Result<PodcastJob> {
        return try {
            val dto = jobApi.getJob(jobId)
            val job = PodcastJob(
                id = dto.id,
                pdfName = dto.pdfName,
                pdfSize = dto.pdfSize,
                summaryMode = dto.summaryMode,
                audioPath = dto.audioPath,
                status = when (dto.status) {
                    "queued" -> JobStatus.QUEUED
                    "processing" -> JobStatus.PROCESSING
                    "completed" -> JobStatus.COMPLETED
                    "failed" -> JobStatus.FAILED
                    else -> JobStatus.QUEUED
                },
                createdAt = dto.createdAt
            )
            Result.success(job)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getJobProgress(jobId: String): Result<JobProgress> {
        return try {
            val dto = jobApi.getJobProgress(jobId)
            val progress = JobProgress(
                jobId = dto.jobId,
                step = PipelineStep.fromValue(dto.step),
                status = dto.status
            )
            Result.success(progress)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getJobResult(jobId: String): Result<JobResult> {
        return try {
            val dto = jobApi.getJobResult(jobId)
            val result = JobResult(
                text = dto.text,
                summary = dto.summary,
                script = dto.script,
                audioUrl = dto.audioUrl,
                jobId = dto.jobId
            )
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}