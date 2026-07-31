package com.pdfpodcaster.android.ui.screens.admin;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000T\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a \u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u0018\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u0003\u001a\"\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0003\u001a\u0010\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0011H\u0003\u001aH\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a\u00b8\u0001\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\b\u0010\"\u001a\u0004\u0018\u00010\u000b2\b\u0010#\u001a\u0004\u0018\u00010\u000b2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010%2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010%2\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u00010%2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00010%2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u00a8\u0006+"}, d2 = {"AdminDashboardScreen", "", "onBack", "Lkotlin/Function0;", "viewModel", "Lcom/pdfpodcaster/android/ui/screens/admin/AdminDashboardViewModel;", "Dialogs", "state", "Lcom/pdfpodcaster/android/ui/screens/admin/AdminState;", "StatCard", "label", "", "value", "modifier", "Landroidx/compose/ui/Modifier;", "StatsSection", "stats", "Lcom/pdfpodcaster/android/domain/model/AdminStats;", "UserCard", "user", "Lcom/pdfpodcaster/android/domain/model/AdminUser;", "onEdit", "onToggleStatus", "onResetPassword", "onDelete", "UserDialog", "title", "email", "plan", "quota", "", "isAdmin", "", "isLoading", "success", "error", "onEmailChange", "Lkotlin/Function1;", "onPlanChange", "onQuotaChange", "onIsAdminChange", "onConfirm", "onDismiss", "app_debug"})
public final class AdminDashboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void AdminDashboardScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.ui.screens.admin.AdminDashboardViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatsSection(com.pdfpodcaster.android.domain.model.AdminStats stats) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatCard(java.lang.String label, java.lang.String value, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void UserCard(com.pdfpodcaster.android.domain.model.AdminUser user, kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, kotlin.jvm.functions.Function0<kotlin.Unit> onToggleStatus, kotlin.jvm.functions.Function0<kotlin.Unit> onResetPassword, kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void Dialogs(com.pdfpodcaster.android.ui.screens.admin.AdminState state, com.pdfpodcaster.android.ui.screens.admin.AdminDashboardViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void UserDialog(java.lang.String title, java.lang.String email, java.lang.String plan, int quota, boolean isAdmin, boolean isLoading, java.lang.String success, java.lang.String error, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onEmailChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPlanChange, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onQuotaChange, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onIsAdminChange, kotlin.jvm.functions.Function0<kotlin.Unit> onConfirm, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
}