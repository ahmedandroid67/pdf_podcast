package com.pdfpodcaster.android.domain.usecase;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/pdfpodcaster/android/domain/usecase/PollJobProgressUseCase;", "", "jobRepository", "Lcom/pdfpodcaster/android/domain/repository/JobRepository;", "(Lcom/pdfpodcaster/android/domain/repository/JobRepository;)V", "invoke", "Lkotlinx/coroutines/flow/Flow;", "Lcom/pdfpodcaster/android/domain/model/JobProgress;", "jobId", "", "intervalMs", "", "app_debug"})
public final class PollJobProgressUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.repository.JobRepository jobRepository = null;
    
    @javax.inject.Inject()
    public PollJobProgressUseCase(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.repository.JobRepository jobRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.pdfpodcaster.android.domain.model.JobProgress> invoke(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, long intervalMs) {
        return null;
    }
}