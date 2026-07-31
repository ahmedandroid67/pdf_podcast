package com.pdfpodcaster.android.ui.screens.jobhistory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdfpodcaster.android.domain.model.JobStatus
import com.pdfpodcaster.android.domain.model.PodcastJob

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobHistoryScreen(
    onBack: () -> Unit,
    onJobClick: (jobId: String) -> Unit,
    onRetryJob: () -> Unit,
    viewModel: JobHistoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Job History", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.ErrorOutline,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(state.error ?: "Failed to load jobs")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadJobs() }) {
                            Text("Retry")
                        }
                    }
                }
            }
            state.jobs.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Description,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "No jobs yet",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            "Create your first podcast to get started",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            else -> {
                RefreshingList(
                    jobs = state.jobs,
                    isRefreshing = state.isRefreshing,
                    onRefresh = { viewModel.refresh() },
                    onJobClick = onJobClick,
                    paddingValues = paddingValues
                )
            }
        }
    }
}

@Composable
private fun RefreshingList(
    jobs: List<PodcastJob>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onJobClick: (String) -> Unit,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            if (isRefreshing) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
        items(jobs) { job ->
            JobCard(job = job, onClick = { onJobClick(job.id) })
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JobCard(job: PodcastJob, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusIcon(status = job.status)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    job.pdfName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatusChip(status = job.status)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        job.createdAt.take(10),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun StatusIcon(status: JobStatus) {
    val icon = when (status) {
        JobStatus.COMPLETED -> Icons.Default.CheckCircle
        JobStatus.FAILED -> Icons.Default.Error
        JobStatus.PROCESSING -> Icons.Default.HourglassTop
        JobStatus.QUEUED -> Icons.Default.Schedule
    }
    val color = when (status) {
        JobStatus.COMPLETED -> MaterialTheme.colorScheme.primary
        JobStatus.FAILED -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
}

@Composable
private fun StatusChip(status: JobStatus) {
    val (label, color) = when (status) {
        JobStatus.COMPLETED -> "Completed" to MaterialTheme.colorScheme.primary
        JobStatus.FAILED -> "Failed" to MaterialTheme.colorScheme.error
        JobStatus.PROCESSING -> "Processing" to MaterialTheme.colorScheme.tertiary
        JobStatus.QUEUED -> "Queued" to MaterialTheme.colorScheme.onSurfaceVariant
    }
    Surface(
        color = color.copy(alpha = 0.12f),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}