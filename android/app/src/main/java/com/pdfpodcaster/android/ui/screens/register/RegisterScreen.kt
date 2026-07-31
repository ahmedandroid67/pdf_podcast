package com.pdfpodcaster.android.ui.screens.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdfpodcaster.android.ui.theme.AppColors

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.registerSuccess) {
        if (state.registerSuccess) {
            onRegisterSuccess()
        }
    }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Create Account",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Start turning PDFs into podcasts",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = viewModel::onEmailChange,
                        label = { Text("Email") },
                        leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = state.password,
                        onValueChange = viewModel::onPasswordChange,
                        label = { Text("Password") },
                        leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Filled.VisibilityOff
                                    else Icons.Filled.Visibility,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        )
                    )

                    if (state.password.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(4) { i ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(4.dp)
                                        .padding(horizontal = 2.dp)
                                        .background(
                                            when {
                                                i < state.passwordStrength -> when (state.passwordStrength) {
                                                    1 -> AppColors.ErrorRed
                                                    2 -> AppColors.WarningYellow
                                                    else -> AppColors.SuccessGreen
                                                }
                                                else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                            },
                                            shape = MaterialTheme.shapes.extraSmall
                                        )
                                )
                            }
                        }
                        Text(
                            text = "Password strength: ${state.passwordStrengthLabel}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = state.confirmPassword,
                        onValueChange = viewModel::onConfirmPasswordChange,
                        label = { Text("Confirm Password") },
                        leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { confirmVisible = !confirmVisible }) {
                                Icon(
                                    if (confirmVisible) Icons.Filled.VisibilityOff
                                    else Icons.Filled.Visibility,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = if (confirmVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        isError = state.confirmPassword.isNotEmpty() &&
                            state.password != state.confirmPassword
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    AnimatedVisibility(visible = state.error != null) {
                        state.error?.let { error ->
                            Text(
                                text = error,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            )
                        }
                    }

                    Button(
                        onClick = viewModel::register,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !state.isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Create Account", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onNavigateBack) {
                Text("Already have an account? ")
                Text(
                    "Sign in",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}