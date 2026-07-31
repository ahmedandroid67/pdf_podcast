package com.pdfpodcaster.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pdfpodcaster.android.ui.screens.admin.AdminDashboardScreen
import com.pdfpodcaster.android.ui.screens.audioplayer.AudioPlayerScreen
import com.pdfpodcaster.android.ui.screens.dashboard.DashboardScreen
import com.pdfpodcaster.android.ui.screens.jobhistory.JobHistoryScreen
import com.pdfpodcaster.android.ui.screens.login.LoginScreen
import com.pdfpodcaster.android.ui.screens.oauthcallback.OAuthCallbackScreen
import com.pdfpodcaster.android.ui.screens.pipeline.PodcastPipelineScreen
import com.pdfpodcaster.android.ui.screens.register.RegisterScreen

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val DASHBOARD = "dashboard"
    const val PIPELINE = "pipeline"
    const val JOB_HISTORY = "job_history"
    const val AUDIO_PLAYER = "audio_player/{jobId}"
    const val ADMIN = "admin"
    const val OAUTH_CALLBACK = "oauth-callback"

    fun audioPlayer(jobId: String) = "audio_player/$jobId"
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val startDestination = Routes.LOGIN

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onNavigateToPipeline = { navController.navigate(Routes.PIPELINE) },
                onNavigateToHistory = { navController.navigate(Routes.JOB_HISTORY) },
                onNavigateToAdmin = { navController.navigate(Routes.ADMIN) },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.PIPELINE) {
            PodcastPipelineScreen(
                onBack = { navController.popBackStack() },
                onJobComplete = { jobId ->
                    navController.navigate(Routes.audioPlayer(jobId))
                }
            )
        }

        composable(Routes.JOB_HISTORY) {
            JobHistoryScreen(
                onBack = { navController.popBackStack() },
                onJobClick = { jobId ->
                    navController.navigate(Routes.audioPlayer(jobId))
                },
                onRetryJob = { navController.navigate(Routes.PIPELINE) }
            )
        }

        composable(
            route = Routes.AUDIO_PLAYER,
            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId") ?: return@composable
            AudioPlayerScreen(
                jobId = jobId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.ADMIN) {
            AdminDashboardScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.OAUTH_CALLBACK) {
            OAuthCallbackScreen(
                onSuccess = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onError = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}