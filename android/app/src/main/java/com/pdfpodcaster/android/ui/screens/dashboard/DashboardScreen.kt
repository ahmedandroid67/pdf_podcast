package com.pdfpodcaster.android.ui.screens.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdfpodcaster.android.domain.model.JobStatus
import com.pdfpodcaster.android.domain.model.PlanType
import com.pdfpodcaster.android.domain.model.PodcastJob
import com.pdfpodcaster.android.ui.components.PlanBadge
import com.pdfpodcaster.android.ui.components.QuotaBar
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToPipeline: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToAdmin: () -> Unit,
    onLogout: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadJobs()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "PdfPodcaster",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    if (state.user?.isAdmin == true) {
                        IconButton(onClick = onNavigateToAdmin) {
                            Icon(Icons.Default.AdminPanelSettings, "Admin")
                        }
                    }
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, "Logout")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                state.user?.let { user ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = user.email.take(2).uppercase(),
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = user.email,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            PlanBadge(plan = user.plan)
                            Spacer(modifier = Modifier.height(12.dp))
                            QuotaBar(used = user.usedToday, total = user.dailyQuota)
                            Spacer(modifier = Modifier.height(8.dp))
                            TextButton(onClick = viewModel::showChangePasswordDialog) {
                                Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Change Password")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TabRow(selectedTabIndex = selectedTab) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Create Podcast") },
                        icon = { Icon(Icons.Default.AddCircle, contentDescription = null) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Job History") },
                        icon = { Icon(Icons.Default.History, contentDescription = null) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                when (selectedTab) {
                    0 -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = onNavigateToPipeline,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = MaterialTheme.shapes.large
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Start New Podcast", fontWeight = FontWeight.SemiBold)
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        "How it works",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    val steps = listOf(
                                        "Upload a PDF or paste a URL",
                                        "AI extracts and summarizes the text",
                                        "A podcast script is generated",
                                        "Audio is created from the script",
                                        "Listen, download, or share your podcast"
                                    )
                                    steps.forEachIndexed { index, step ->
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                "${index + 1}.",
                                                color = MaterialTheme.colorScheme.primary,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(step, style = MaterialTheme.typography.bodyMedium)
                                        }
                                        if (index < steps.lastIndex) Spacer(modifier = Modifier.height(4.dp))
                                    }
                                }
                            }
                        }
                    }
                    1 -> {
                        if (state.jobs.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
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
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        "Create your first podcast to get started",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        } else {
                            LazyColumn {
                                items(state.jobs) { job ->
                                    JobItem(job = job, onClick = { onNavigateToHistory() })
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (state.showChangePasswordDialog) {
        AlertDialog(
            onDismissRequest = viewModel::hideChangePasswordDialog,
            title = { Text("Change Password") },
            text = {
                Column {
                    OutlinedTextField(
                        value = state.newPassword,
                        onValueChange = viewModel::onNewPasswordChange,
                        label = { Text("New Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (state.passwordChangeError != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            state.passwordChangeError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    if (state.passwordChangeSuccess != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            state.passwordChangeSuccess!!,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = viewModel::changePassword,
                    enabled = !state.isChangingPassword
                ) {
                    if (state.isChangingPassword) CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    ) else Text("Update")
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::hideChangePasswordDialog) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JobItem(job: PodcastJob, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                when (job.status) {
                    JobStatus.COMPLETED -> Icons.Default.CheckCircle
                    JobStatus.FAILED -> Icons.Default.Error
                    JobStatus.PROCESSING -> Icons.Default.HourglassTop
                    else -> Icons.Default.Schedule
                },
                contentDescription = null,
                tint = when (job.status) {
                    JobStatus.COMPLETED -> MaterialTheme.colorScheme.primary
                    JobStatus.FAILED -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                },
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    job.pdfName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    job.status.name.lowercase().replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodySmall,
                    color = when (job.status) {
                        JobStatus.COMPLETED -> MaterialTheme.colorScheme.primary
                        JobStatus.FAILED -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
            Text(
                job.createdAt.take(10),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}