package com.pdfpodcaster.android.ui.screens.audioplayer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00004\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a^\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a(\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0007\u001a\u0018\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u000fH\u0003\u001a\u0010\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0005H\u0002\u00a8\u0006\u0018"}, d2 = {"AudioControls", "", "isPlaying", "", "currentPosition", "", "duration", "onPlayPause", "Lkotlin/Function0;", "onSeek", "Lkotlin/Function1;", "onRewind", "onForward", "AudioPlayerScreen", "jobId", "", "onBack", "viewModel", "Lcom/pdfpodcaster/android/ui/screens/audioplayer/AudioPlayerViewModel;", "CollapsibleSection", "title", "content", "formatTime", "millis", "app_debug"})
public final class AudioPlayerScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void AudioPlayerScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.ui.screens.audioplayer.AudioPlayerViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AudioControls(boolean isPlaying, long currentPosition, long duration, kotlin.jvm.functions.Function0<kotlin.Unit> onPlayPause, kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onSeek, kotlin.jvm.functions.Function0<kotlin.Unit> onRewind, kotlin.jvm.functions.Function0<kotlin.Unit> onForward) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CollapsibleSection(java.lang.String title, java.lang.String content) {
    }
    
    private static final java.lang.String formatTime(long millis) {
        return null;
    }
}