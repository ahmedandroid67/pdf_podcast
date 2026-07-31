package com.pdfpodcaster.android.ui.screens.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0013J\u0006\u0010\u0015\u001a\u00020\u0013J\b\u0010\u0016\u001a\u00020\u0013H\u0002J\u0006\u0010\u0017\u001a\u00020\u0013J\u000e\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001aJ\u0006\u0010\u001b\u001a\u00020\u0013R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001c"}, d2 = {"Lcom/pdfpodcaster/android/ui/screens/dashboard/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "getCurrentUserUseCase", "Lcom/pdfpodcaster/android/domain/usecase/GetCurrentUserUseCase;", "logoutUseCase", "Lcom/pdfpodcaster/android/domain/usecase/LogoutUseCase;", "getJobsUseCase", "Lcom/pdfpodcaster/android/domain/usecase/GetJobsUseCase;", "changePasswordUseCase", "Lcom/pdfpodcaster/android/domain/usecase/ChangePasswordUseCase;", "(Lcom/pdfpodcaster/android/domain/usecase/GetCurrentUserUseCase;Lcom/pdfpodcaster/android/domain/usecase/LogoutUseCase;Lcom/pdfpodcaster/android/domain/usecase/GetJobsUseCase;Lcom/pdfpodcaster/android/domain/usecase/ChangePasswordUseCase;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/pdfpodcaster/android/ui/screens/dashboard/DashboardState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "changePassword", "", "hideChangePasswordDialog", "loadJobs", "loadUserData", "logout", "onNewPasswordChange", "password", "", "showChangePasswordDialog", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.GetCurrentUserUseCase getCurrentUserUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.LogoutUseCase logoutUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.GetJobsUseCase getJobsUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.ChangePasswordUseCase changePasswordUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pdfpodcaster.android.ui.screens.dashboard.DashboardState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pdfpodcaster.android.ui.screens.dashboard.DashboardState> state = null;
    
    @javax.inject.Inject()
    public DashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.GetCurrentUserUseCase getCurrentUserUseCase, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.LogoutUseCase logoutUseCase, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.GetJobsUseCase getJobsUseCase, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.ChangePasswordUseCase changePasswordUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pdfpodcaster.android.ui.screens.dashboard.DashboardState> getState() {
        return null;
    }
    
    private final void loadUserData() {
    }
    
    public final void loadJobs() {
    }
    
    public final void showChangePasswordDialog() {
    }
    
    public final void hideChangePasswordDialog() {
    }
    
    public final void onNewPasswordChange(@org.jetbrains.annotations.NotNull()
    java.lang.String password) {
    }
    
    public final void changePassword() {
    }
    
    public final void logout() {
    }
}