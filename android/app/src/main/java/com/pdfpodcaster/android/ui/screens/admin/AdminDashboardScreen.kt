package com.pdfpodcaster.android.ui.screens.admin

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdfpodcaster.android.domain.model.AdminStats
import com.pdfpodcaster.android.domain.model.AdminUser
import com.pdfpodcaster.android.domain.model.PlanType
import com.pdfpodcaster.android.ui.components.PlanBadge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onBack: () -> Unit,
    viewModel: AdminDashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Dashboard", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.showCreateDialog() }) {
                        Icon(Icons.Default.PersonAdd, "Add User")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.showCreateDialog() }) {
                Icon(Icons.Default.Add, "Add User")
            }
        }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            state.error != null -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(state.error ?: "Error", color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { /* retry */ }) { Text("Retry") }
                    }
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    state.stats?.let { stats -> StatsSection(stats) }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Users", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        OutlinedButton(onClick = { viewModel.bulkDeleteOldUsers() }) {
                            Icon(Icons.Default.DeleteSweep, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Cleanup Old")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(state.users) { user ->
                            UserCard(
                                user = user,
                                onEdit = { viewModel.showEditDialog(user) },
                                onToggleStatus = { viewModel.toggleUserStatus(user) },
                                onResetPassword = { viewModel.showResetPasswordDialog(user) },
                                onDelete = { viewModel.showDeleteConfirm(user) }
                            )
                        }
                    }
                }
            }
        }
    }

    Dialogs(state, viewModel)
}

@Composable
private fun StatsSection(stats: AdminStats) {
    Column {
        Text("Platform Statistics", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatCard("Total Users", stats.totalUsers.toString(), Modifier.weight(1f))
            StatCard("Active Users", stats.activeUsers.toString(), Modifier.weight(1f))
            StatCard("Total Jobs", stats.totalJobs.toString(), Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatCard("Gemini Uses", stats.totalGeminiUses.toString(), Modifier.weight(1f))
            StatCard("Tokens Today", stats.totalTokensUsedToday.toString(), Modifier.weight(1f))
            StatCard("Remaining", stats.remainingTokens.toString(), Modifier.weight(1f))
        }
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text(label, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
private fun UserCard(
    user: AdminUser,
    onEdit: () -> Unit,
    onToggleStatus: () -> Unit,
    onResetPassword: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(user.email, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.width(8.dp))
                        PlanBadge(plan = user.plan)
                        if (user.isAdmin) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Surface(color = MaterialTheme.colorScheme.error.copy(alpha = 0.15f), shape = MaterialTheme.shapes.small) {
                                Text(" Admin ", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(horizontal = 4.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        Text("Quota: ${user.usedToday}/${user.dailyQuota}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.width(12.dp))
                        val statusColor = if (user.isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        Text(if (user.isActive) "Active" else "Inactive", style = MaterialTheme.typography.bodySmall, color = statusColor, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                OutlinedButton(onClick = onEdit, contentPadding = PaddingValues(horizontal = 8.dp)) {
                    Icon(Icons.Default.Edit, null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Edit", style = MaterialTheme.typography.labelSmall)
                }
                OutlinedButton(onClick = onToggleStatus, contentPadding = PaddingValues(horizontal = 8.dp)) {
                    Icon(if (user.isActive) Icons.Default.Block else Icons.Default.CheckCircle, null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (user.isActive) "Disable" else "Enable", style = MaterialTheme.typography.labelSmall)
                }
                OutlinedButton(onClick = onResetPassword, contentPadding = PaddingValues(horizontal = 8.dp)) {
                    Icon(Icons.Default.Lock, null, modifier = Modifier.size(16.dp))
                }
                OutlinedButton(onClick = onDelete, colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error), contentPadding = PaddingValues(horizontal = 8.dp)) {
                    Icon(Icons.Default.Delete, null, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
private fun Dialogs(state: AdminState, viewModel: AdminDashboardViewModel) {
    if (state.showCreateDialog) {
        UserDialog(
            title = "Create User",
            email = state.dialogEmail,
            plan = state.dialogPlan,
            quota = state.dialogQuota,
            isAdmin = state.dialogIsAdmin,
            isLoading = state.isDialogLoading,
            success = state.dialogSuccess,
            error = state.dialogError,
            onEmailChange = viewModel::onDialogEmailChange,
            onPlanChange = viewModel::onDialogPlanChange,
            onQuotaChange = viewModel::onDialogQuotaChange,
            onIsAdminChange = viewModel::onDialogIsAdminChange,
            onConfirm = viewModel::createUser,
            onDismiss = viewModel::hideDialog
        )
    }

    if (state.showEditDialog) {
        UserDialog(
            title = "Edit User",
            email = state.dialogEmail,
            plan = state.dialogPlan,
            quota = state.dialogQuota,
            isAdmin = state.dialogIsAdmin,
            isLoading = state.isDialogLoading,
            success = state.dialogSuccess,
            error = state.dialogError,
            onEmailChange = viewModel::onDialogEmailChange,
            onPlanChange = viewModel::onDialogPlanChange,
            onQuotaChange = viewModel::onDialogQuotaChange,
            onIsAdminChange = viewModel::onDialogIsAdminChange,
            onConfirm = viewModel::updateUser,
            onDismiss = viewModel::hideDialog
        )
    }

    if (state.showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = viewModel::hideDialog,
            title = { Text("Delete User") },
            text = { Text("Are you sure you want to delete ${state.selectedUser?.email}? This cannot be undone.") },
            confirmButton = {
                Button(onClick = viewModel::deleteUser, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                    Text("Delete")
                }
            },
            dismissButton = { TextButton(onClick = viewModel::hideDialog) { Text("Cancel") } }
        )
    }

    if (state.showResetPasswordDialog) {
        var newPassword by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = viewModel::hideDialog,
            title = { Text("Reset Password") },
            text = {
                Column {
                    OutlinedTextField(value = newPassword, onValueChange = { newPassword = it; viewModel.onDialogPasswordChange(it) }, label = { Text("New Password") }, singleLine = true)
                    if (state.dialogError != null) { Spacer(modifier = Modifier.height(4.dp)); Text(state.dialogError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
                    if (state.dialogSuccess != null) { Spacer(modifier = Modifier.height(4.dp)); Text(state.dialogSuccess!!, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall) }
                }
            },
            confirmButton = { Button(onClick = viewModel::resetPassword, enabled = newPassword.length >= 8) { Text("Reset") } },
            dismissButton = { TextButton(onClick = viewModel::hideDialog) { Text("Cancel") } }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserDialog(
    title: String,
    email: String,
    plan: String,
    quota: Int,
    isAdmin: Boolean,
    isLoading: Boolean,
    success: String?,
    error: String?,
    onEmailChange: (String) -> Unit,
    onPlanChange: (String) -> Unit,
    onQuotaChange: (Int) -> Unit,
    onIsAdminChange: (Boolean) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val plans = listOf("free", "basic", "pro", "business")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = email, onValueChange = onEmailChange, label = { Text("Email") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                var planExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(expanded = planExpanded, onExpandedChange = { planExpanded = it }) {
                    OutlinedTextField(value = plan.replaceFirstChar { it.uppercase() }, onValueChange = {}, readOnly = true, label = { Text("Plan") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = planExpanded) }, modifier = Modifier.menuAnchor().fillMaxWidth())
                    ExposedDropdownMenu(expanded = planExpanded, onDismissRequest = { planExpanded = false }) {
                        plans.forEach { p -> DropdownMenuItem(text = { Text(p.replaceFirstChar { it.uppercase() }) }, onClick = { onPlanChange(p); planExpanded = false }) }
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) { Text("Daily Quota: $quota"); Spacer(modifier = Modifier.width(8.dp)); IconButton(onClick = { onQuotaChange((quota - 1).coerceAtLeast(1)) }) { Icon(Icons.Default.Remove, null) }; Text(quota.toString()); IconButton(onClick = { onQuotaChange(quota + 1) }) { Icon(Icons.Default.Add, null) } }
                Row(verticalAlignment = Alignment.CenterVertically) { Checkbox(checked = isAdmin, onCheckedChange = onIsAdminChange); Spacer(modifier = Modifier.width(4.dp)); Text("Admin") }
                if (error != null) Text(error, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                if (success != null) Text(success, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall)
            }
        },
        confirmButton = { Button(onClick = onConfirm, enabled = !isLoading) { if (isLoading) CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp) else Text("Save") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}