package com.pdfpodcaster.android.ui.screens.jobhistory;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000f"}, d2 = {"Lcom/pdfpodcaster/android/ui/screens/jobhistory/JobHistoryViewModel;", "Landroidx/lifecycle/ViewModel;", "getJobsUseCase", "Lcom/pdfpodcaster/android/domain/usecase/GetJobsUseCase;", "(Lcom/pdfpodcaster/android/domain/usecase/GetJobsUseCase;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/pdfpodcaster/android/ui/screens/jobhistory/JobHistoryState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "loadJobs", "", "refresh", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class JobHistoryViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.GetJobsUseCase getJobsUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pdfpodcaster.android.ui.screens.jobhistory.JobHistoryState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pdfpodcaster.android.ui.screens.jobhistory.JobHistoryState> state = null;
    
    @javax.inject.Inject()
    public JobHistoryViewModel(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.GetJobsUseCase getJobsUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pdfpodcaster.android.ui.screens.jobhistory.JobHistoryState> getState() {
        return null;
    }
    
    public final void loadJobs() {
    }
    
    public final void refresh() {
    }
}