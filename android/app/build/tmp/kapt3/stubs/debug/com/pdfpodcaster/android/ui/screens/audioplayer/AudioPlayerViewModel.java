package com.pdfpodcaster.android.ui.screens.audioplayer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B!\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0016J\b\u0010\u0017\u001a\u00020\u0013H\u0014J\u0006\u0010\u0018\u001a\u00020\u0013J\u0006\u0010\u0019\u001a\u00020\u0013J\u000e\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u001cJ\u0010\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u0016H\u0002J\u0006\u0010\u001f\u001a\u00020\u0013R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/pdfpodcaster/android/ui/screens/audioplayer/AudioPlayerViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "getJobResultUseCase", "Lcom/pdfpodcaster/android/domain/usecase/GetJobResultUseCase;", "tokenDataStore", "Lcom/pdfpodcaster/android/data/local/TokenDataStore;", "(Landroid/content/Context;Lcom/pdfpodcaster/android/domain/usecase/GetJobResultUseCase;Lcom/pdfpodcaster/android/data/local/TokenDataStore;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/pdfpodcaster/android/ui/screens/audioplayer/AudioPlayerState;", "player", "Landroidx/media3/exoplayer/ExoPlayer;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "forward", "", "loadResult", "jobId", "", "onCleared", "release", "rewind", "seekTo", "position", "", "setupPlayer", "audioUrlPath", "togglePlayback", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AudioPlayerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.GetJobResultUseCase getJobResultUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.data.local.TokenDataStore tokenDataStore = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pdfpodcaster.android.ui.screens.audioplayer.AudioPlayerState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pdfpodcaster.android.ui.screens.audioplayer.AudioPlayerState> state = null;
    @org.jetbrains.annotations.Nullable()
    private androidx.media3.exoplayer.ExoPlayer player;
    
    @javax.inject.Inject()
    public AudioPlayerViewModel(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.GetJobResultUseCase getJobResultUseCase, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.local.TokenDataStore tokenDataStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pdfpodcaster.android.ui.screens.audioplayer.AudioPlayerState> getState() {
        return null;
    }
    
    public final void loadResult(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId) {
    }
    
    private final void setupPlayer(java.lang.String audioUrlPath) {
    }
    
    public final void togglePlayback() {
    }
    
    public final void seekTo(long position) {
    }
    
    public final void rewind() {
    }
    
    public final void forward() {
    }
    
    public final void release() {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
}