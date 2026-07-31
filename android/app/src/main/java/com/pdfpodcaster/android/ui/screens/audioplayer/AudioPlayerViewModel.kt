package com.pdfpodcaster.android.ui.screens.audioplayer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.pdfpodcaster.android.data.local.TokenDataStore
import com.pdfpodcaster.android.domain.model.JobResult
import com.pdfpodcaster.android.domain.usecase.GetJobResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AudioPlayerState(
    val isLoading: Boolean = true,
    val result: JobResult? = null,
    val error: String? = null,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long = 0L
)

@HiltViewModel
class AudioPlayerViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getJobResultUseCase: GetJobResultUseCase,
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(AudioPlayerState())
    val state: StateFlow<AudioPlayerState> = _state.asStateFlow()

    private var player: ExoPlayer? = null

    fun loadResult(jobId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            getJobResultUseCase(jobId)
                .onSuccess { result ->
                    _state.value = _state.value.copy(isLoading = false, result = result)
                    result.audioUrl?.let { audioUrl ->
                        setupPlayer(audioUrl)
                    }
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load result"
                    )
                }
        }
    }

    private fun setupPlayer(audioUrlPath: String) {
        viewModelScope.launch {
            val token = tokenDataStore.getToken()
            val baseUrl = com.pdfpodcaster.android.BuildConfig.API_BASE_URL
            val fullUrl = "$baseUrl$audioUrlPath"

            player = ExoPlayer.Builder(context).build().apply {
                val mediaItem = MediaItem.fromUri(fullUrl)
                setMediaItem(mediaItem)
                prepare()
            }

            player?.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    _state.value = _state.value.copy(isPlaying = isPlaying)
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_READY -> {
                            _state.value = _state.value.copy(
                                duration = player?.duration ?: 0L
                            )
                        }
                        Player.STATE_ENDED -> {
                            _state.value = _state.value.copy(isPlaying = false)
                        }
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    _state.value = _state.value.copy(error = error.message)
                }
            })
        }

        viewModelScope.launch {
            while (true) {
                kotlinx.coroutines.delay(200)
                player?.let { p ->
                    if (p.isPlaying || p.playbackState == Player.STATE_READY) {
                        _state.value = _state.value.copy(
                            currentPosition = p.currentPosition,
                            duration = p.duration.coerceAtLeast(0L)
                        )
                    }
                }
            }
        }
    }

    fun togglePlayback() {
        player?.let {
            if (it.isPlaying) it.pause() else it.play()
        }
    }

    fun seekTo(position: Long) {
        player?.seekTo(position)
    }

    fun rewind() {
        player?.seekTo(maxOf(0, (player?.currentPosition ?: 0) - 10000))
    }

    fun forward() {
        player?.let { p ->
            p.seekTo(minOf(p.duration, p.currentPosition + 10000))
        }
    }

    fun release() {
        player?.release()
        player = null
    }

    override fun onCleared() {
        super.onCleared()
        release()
    }
}