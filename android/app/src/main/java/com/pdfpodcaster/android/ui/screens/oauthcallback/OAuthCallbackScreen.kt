package com.pdfpodcaster.android.ui.screens.oauthcallback

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfpodcaster.android.data.local.TokenDataStore
import com.pdfpodcaster.android.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OAuthState(
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class OAuthCallbackViewModel @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OAuthState())
    val state: StateFlow<OAuthState> = _state.asStateFlow()

    fun processToken(token: String) {
        viewModelScope.launch {
            tokenDataStore.saveToken(token)
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun setError(message: String) {
        _state.value = _state.value.copy(isLoading = false, error = message)
    }
}

@Composable
fun OAuthCallbackScreen(
    onSuccess: () -> Unit,
    onError: () -> Unit,
    viewModel: OAuthCallbackViewModel = hiltViewModel()
) {
    // This screen handles the deep link from Google OAuth
    // The token extraction happens in MainActivity via the intent filter
    // This screen shows a loading state while the token is being processed

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Completing sign in...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}