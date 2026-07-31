package com.pdfpodcaster.android.domain.usecase

import com.pdfpodcaster.android.domain.model.JobProgress
import com.pdfpodcaster.android.domain.model.JobResult
import com.pdfpodcaster.android.domain.model.PodcastJob
import com.pdfpodcaster.android.domain.repository.JobRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class ProcessUploadUseCase @Inject constructor(
    private val jobRepository: JobRepository
) {
    suspend operator fun invoke(file: File, fileName: String, lang: String, mode: String): Result<String> {
        return jobRepository.processUpload(file, fileName, lang, mode)
    }
}

class ProcessUrlUseCase @Inject constructor(
    private val jobRepository: JobRepository
) {
    suspend operator fun invoke(url: String, lang: String, mode: String): Result<String> {
        return jobRepository.processUrl(url, lang, mode)
    }
}

class GetJobsUseCase @Inject constructor(
    private val jobRepository: JobRepository
) {
    suspend operator fun invoke(): Result<List<PodcastJob>> {
        return jobRepository.getJobs()
    }
}

class PollJobProgressUseCase @Inject constructor(
    private val jobRepository: JobRepository
) {
    fun invoke(jobId: String, intervalMs: Long = 2500L): Flow<JobProgress> = flow {
        while (true) {
            val result = jobRepository.getJobProgress(jobId)
            result.getOrNull()?.let { progress ->
                emit(progress)
                if (progress.step == com.pdfpodcaster.android.domain.model.PipelineStep.DONE ||
                    progress.step == com.pdfpodcaster.android.domain.model.PipelineStep.FAILED
                ) {
                    return@flow
                }
            }
            delay(intervalMs)
        }
    }
}

class GetJobResultUseCase @Inject constructor(
    private val jobRepository: JobRepository
) {
    suspend operator fun invoke(jobId: String): Result<JobResult> {
        return jobRepository.getJobResult(jobId)
    }
}