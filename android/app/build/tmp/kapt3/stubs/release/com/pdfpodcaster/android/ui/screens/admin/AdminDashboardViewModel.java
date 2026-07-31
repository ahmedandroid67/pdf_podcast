package com.pdfpodcaster.android.ui.screens.admin;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001BG\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u0012J\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u001bJ\u0006\u0010\u001d\u001a\u00020\u001bJ\u0006\u0010\u001e\u001a\u00020\u001bJ\b\u0010\u001f\u001a\u00020\u001bH\u0002J\u000e\u0010 \u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020%J\u000e\u0010&\u001a\u00020\u001b2\u0006\u0010\'\u001a\u00020\"J\u000e\u0010(\u001a\u00020\u001b2\u0006\u0010)\u001a\u00020\"J\u000e\u0010*\u001a\u00020\u001b2\u0006\u0010+\u001a\u00020,J\u0006\u0010-\u001a\u00020\u001bJ\u0006\u0010.\u001a\u00020\u001bJ\u000e\u0010/\u001a\u00020\u001b2\u0006\u00100\u001a\u000201J\u000e\u00102\u001a\u00020\u001b2\u0006\u00100\u001a\u000201J\u000e\u00103\u001a\u00020\u001b2\u0006\u00100\u001a\u000201J\u000e\u00104\u001a\u00020\u001b2\u0006\u00100\u001a\u000201J\u0006\u00105\u001a\u00020\u001bR\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00066"}, d2 = {"Lcom/pdfpodcaster/android/ui/screens/admin/AdminDashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "getAdminStatsUseCase", "Lcom/pdfpodcaster/android/domain/usecase/GetAdminStatsUseCase;", "getAdminUsersUseCase", "Lcom/pdfpodcaster/android/domain/usecase/GetAdminUsersUseCase;", "createAdminUserUseCase", "Lcom/pdfpodcaster/android/domain/usecase/CreateAdminUserUseCase;", "updateAdminUserUseCase", "Lcom/pdfpodcaster/android/domain/usecase/UpdateAdminUserUseCase;", "toggleAdminUserStatusUseCase", "Lcom/pdfpodcaster/android/domain/usecase/ToggleAdminUserStatusUseCase;", "resetAdminPasswordUseCase", "Lcom/pdfpodcaster/android/domain/usecase/ResetAdminPasswordUseCase;", "deleteAdminUserUseCase", "Lcom/pdfpodcaster/android/domain/usecase/DeleteAdminUserUseCase;", "bulkDeleteOldUsersUseCase", "Lcom/pdfpodcaster/android/domain/usecase/BulkDeleteOldUsersUseCase;", "(Lcom/pdfpodcaster/android/domain/usecase/GetAdminStatsUseCase;Lcom/pdfpodcaster/android/domain/usecase/GetAdminUsersUseCase;Lcom/pdfpodcaster/android/domain/usecase/CreateAdminUserUseCase;Lcom/pdfpodcaster/android/domain/usecase/UpdateAdminUserUseCase;Lcom/pdfpodcaster/android/domain/usecase/ToggleAdminUserStatusUseCase;Lcom/pdfpodcaster/android/domain/usecase/ResetAdminPasswordUseCase;Lcom/pdfpodcaster/android/domain/usecase/DeleteAdminUserUseCase;Lcom/pdfpodcaster/android/domain/usecase/BulkDeleteOldUsersUseCase;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/pdfpodcaster/android/ui/screens/admin/AdminState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "bulkDeleteOldUsers", "", "createUser", "deleteUser", "hideDialog", "loadData", "onDialogEmailChange", "email", "", "onDialogIsAdminChange", "isAdmin", "", "onDialogPasswordChange", "password", "onDialogPlanChange", "plan", "onDialogQuotaChange", "quota", "", "resetPassword", "showCreateDialog", "showDeleteConfirm", "user", "Lcom/pdfpodcaster/android/domain/model/AdminUser;", "showEditDialog", "showResetPasswordDialog", "toggleUserStatus", "updateUser", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AdminDashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.GetAdminStatsUseCase getAdminStatsUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.GetAdminUsersUseCase getAdminUsersUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.CreateAdminUserUseCase createAdminUserUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.UpdateAdminUserUseCase updateAdminUserUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.ToggleAdminUserStatusUseCase toggleAdminUserStatusUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.ResetAdminPasswordUseCase resetAdminPasswordUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.DeleteAdminUserUseCase deleteAdminUserUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.BulkDeleteOldUsersUseCase bulkDeleteOldUsersUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pdfpodcaster.android.ui.screens.admin.AdminState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pdfpodcaster.android.ui.screens.admin.AdminState> state = null;
    
    @javax.inject.Inject()
    public AdminDashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.GetAdminStatsUseCase getAdminStatsUseCase, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.GetAdminUsersUseCase getAdminUsersUseCase, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.CreateAdminUserUseCase createAdminUserUseCase, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.UpdateAdminUserUseCase updateAdminUserUseCase, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.ToggleAdminUserStatusUseCase toggleAdminUserStatusUseCase, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.ResetAdminPasswordUseCase resetAdminPasswordUseCase, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.DeleteAdminUserUseCase deleteAdminUserUseCase, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.BulkDeleteOldUsersUseCase bulkDeleteOldUsersUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pdfpodcaster.android.ui.screens.admin.AdminState> getState() {
        return null;
    }
    
    private final void loadData() {
    }
    
    public final void showCreateDialog() {
    }
    
    public final void hideDialog() {
    }
    
    public final void showEditDialog(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.model.AdminUser user) {
    }
    
    public final void showDeleteConfirm(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.model.AdminUser user) {
    }
    
    public final void showResetPasswordDialog(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.model.AdminUser user) {
    }
    
    public final void onDialogEmailChange(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
    }
    
    public final void onDialogPlanChange(@org.jetbrains.annotations.NotNull()
    java.lang.String plan) {
    }
    
    public final void onDialogQuotaChange(int quota) {
    }
    
    public final void onDialogIsAdminChange(boolean isAdmin) {
    }
    
    public final void onDialogPasswordChange(@org.jetbrains.annotations.NotNull()
    java.lang.String password) {
    }
    
    public final void createUser() {
    }
    
    public final void updateUser() {
    }
    
    public final void toggleUserStatus(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.model.AdminUser user) {
    }
    
    public final void resetPassword() {
    }
    
    public final void deleteUser() {
    }
    
    public final void bulkDeleteOldUsers() {
    }
}