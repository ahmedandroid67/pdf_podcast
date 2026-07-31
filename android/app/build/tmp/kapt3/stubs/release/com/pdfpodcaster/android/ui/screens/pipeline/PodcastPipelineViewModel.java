package com.pdfpodcaster.android.ui.screens.pipeline;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0082@\u00a2\u0006\u0002\u0010\u0016J\u000e\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0019J\u001a\u0010\u001a\u001a\u00020\u00132\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0015J\u000e\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u001fJ\u000e\u0010 \u001a\u00020\u00132\u0006\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\u00132\u0006\u0010$\u001a\u00020\u0015J\u0006\u0010%\u001a\u00020\u0013J\u0010\u0010&\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\'\u001a\u00020\u00132\n\b\u0002\u0010(\u001a\u0004\u0018\u00010)R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006*"}, d2 = {"Lcom/pdfpodcaster/android/ui/screens/pipeline/PodcastPipelineViewModel;", "Landroidx/lifecycle/ViewModel;", "processUploadUseCase", "Lcom/pdfpodcaster/android/domain/usecase/ProcessUploadUseCase;", "processUrlUseCase", "Lcom/pdfpodcaster/android/domain/usecase/ProcessUrlUseCase;", "pollJobProgressUseCase", "Lcom/pdfpodcaster/android/domain/usecase/PollJobProgressUseCase;", "getJobResultUseCase", "Lcom/pdfpodcaster/android/domain/usecase/GetJobResultUseCase;", "(Lcom/pdfpodcaster/android/domain/usecase/ProcessUploadUseCase;Lcom/pdfpodcaster/android/domain/usecase/ProcessUrlUseCase;Lcom/pdfpodcaster/android/domain/usecase/PollJobProgressUseCase;Lcom/pdfpodcaster/android/domain/usecase/GetJobResultUseCase;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/pdfpodcaster/android/ui/screens/pipeline/PipelineState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "fetchResult", "", "jobId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onAudioModeChange", "mode", "Lcom/pdfpodcaster/android/domain/model/AudioMode;", "onFileSelected", "uri", "Landroid/net/Uri;", "fileName", "onInputModeChange", "Lcom/pdfpodcaster/android/ui/screens/pipeline/InputMode;", "onLanguageChange", "lang", "Lcom/pdfpodcaster/android/domain/model/PodcastLanguage;", "onUrlChange", "url", "reset", "startPolling", "startProcessing", "file", "Ljava/io/File;", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class PodcastPipelineViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.ProcessUploadUseCase processUploadUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.ProcessUrlUseCase processUrlUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.PollJobProgressUseCase pollJobProgressUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.GetJobResultUseCase getJobResultUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pdfpodcaster.android.ui.screens.pipeline.PipelineState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pdfpodcaster.android.ui.screens.pipeline.PipelineState> state = null;
    
    @javax.inject.Inject()
    public PodcastPipelineViewModel(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.ProcessUploadUseCase processUploadUseCase, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.ProcessUrlUseCase processUrlUseCase, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.PollJobProgressUseCase pollJobProgressUseCase, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.GetJobResultUseCase getJobResultUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pdfpodcaster.android.ui.screens.pipeline.PipelineState> getState() {
        return null;
    }
    
    public final void onInputModeChange(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.ui.screens.pipeline.InputMode mode) {
    }
    
    public final void onFileSelected(@org.jetbrains.annotations.Nullable()
    android.net.Uri uri, @org.jetbrains.annotations.Nullable()
    java.lang.String fileName) {
    }
    
    public final void onUrlChange(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
    }
    
    public final void onAudioModeChange(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.model.AudioMode mode) {
    }
    
    public final void onLanguageChange(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.model.PodcastLanguage lang) {
    }
    
    public final void startProcessing(@org.jetbrains.annotations.Nullable()
    java.io.File file) {
    }
    
    private final void startPolling(java.lang.String jobId) {
    }
    
    private final java.lang.Object fetchResult(java.lang.String jobId, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void reset() {
    }
}