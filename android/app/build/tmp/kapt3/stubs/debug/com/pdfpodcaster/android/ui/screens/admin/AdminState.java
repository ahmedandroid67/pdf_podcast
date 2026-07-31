package com.pdfpodcaster.android.ui.screens.admin;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b2\b\u0086\b\u0018\u00002\u00020\u0001B\u00bf\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\f\u001a\u00020\b\u0012\b\b\u0002\u0010\r\u001a\u00020\b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\b\u0012\b\b\u0002\u0010\u000f\u001a\u00020\b\u0012\b\b\u0002\u0010\u0010\u001a\u00020\n\u0012\b\b\u0002\u0010\u0011\u001a\u00020\n\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\b\u0012\b\b\u0002\u0010\u0015\u001a\u00020\n\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\n\u0012\b\b\u0002\u0010\u0018\u001a\u00020\b\u00a2\u0006\u0002\u0010\u0019J\u000b\u0010/\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\nH\u00c6\u0003J\t\u00101\u001a\u00020\nH\u00c6\u0003J\t\u00102\u001a\u00020\u0013H\u00c6\u0003J\t\u00103\u001a\u00020\bH\u00c6\u0003J\t\u00104\u001a\u00020\nH\u00c6\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\t\u00107\u001a\u00020\bH\u00c6\u0003J\u000f\u00108\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u00109\u001a\u00020\bH\u00c6\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u000b\u0010;\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\t\u0010<\u001a\u00020\bH\u00c6\u0003J\t\u0010=\u001a\u00020\bH\u00c6\u0003J\t\u0010>\u001a\u00020\bH\u00c6\u0003J\t\u0010?\u001a\u00020\bH\u00c6\u0003J\u00c3\u0001\u0010@\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\f\u001a\u00020\b2\b\b\u0002\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u000e\u001a\u00020\b2\b\b\u0002\u0010\u000f\u001a\u00020\b2\b\b\u0002\u0010\u0010\u001a\u00020\n2\b\b\u0002\u0010\u0011\u001a\u00020\n2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\b2\b\b\u0002\u0010\u0015\u001a\u00020\n2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u0018\u001a\u00020\bH\u00c6\u0001J\u0013\u0010A\u001a\u00020\b2\b\u0010B\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010C\u001a\u00020\u0013H\u00d6\u0001J\t\u0010D\u001a\u00020\nH\u00d6\u0001R\u0011\u0010\u0010\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0017\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0011\u0010\u0014\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0015\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0011\u0010\u0011\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001bR\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001bR\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001bR\u0011\u0010\u0018\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u001eR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u001eR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\r\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001eR\u0011\u0010\u000e\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001eR\u0011\u0010\f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001eR\u0011\u0010\u000f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001eR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.\u00a8\u0006E"}, d2 = {"Lcom/pdfpodcaster/android/ui/screens/admin/AdminState;", "", "stats", "Lcom/pdfpodcaster/android/domain/model/AdminStats;", "users", "", "Lcom/pdfpodcaster/android/domain/model/AdminUser;", "isLoading", "", "error", "", "selectedUser", "showEditDialog", "showCreateDialog", "showDeleteConfirm", "showResetPasswordDialog", "dialogEmail", "dialogPlan", "dialogQuota", "", "dialogIsAdmin", "dialogPassword", "dialogSuccess", "dialogError", "isDialogLoading", "(Lcom/pdfpodcaster/android/domain/model/AdminStats;Ljava/util/List;ZLjava/lang/String;Lcom/pdfpodcaster/android/domain/model/AdminUser;ZZZZLjava/lang/String;Ljava/lang/String;IZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V", "getDialogEmail", "()Ljava/lang/String;", "getDialogError", "getDialogIsAdmin", "()Z", "getDialogPassword", "getDialogPlan", "getDialogQuota", "()I", "getDialogSuccess", "getError", "getSelectedUser", "()Lcom/pdfpodcaster/android/domain/model/AdminUser;", "getShowCreateDialog", "getShowDeleteConfirm", "getShowEditDialog", "getShowResetPasswordDialog", "getStats", "()Lcom/pdfpodcaster/android/domain/model/AdminStats;", "getUsers", "()Ljava/util/List;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class AdminState {
    @org.jetbrains.annotations.Nullable()
    private final com.pdfpodcaster.android.domain.model.AdminStats stats = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.pdfpodcaster.android.domain.model.AdminUser> users = null;
    private final boolean isLoading = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    @org.jetbrains.annotations.Nullable()
    private final com.pdfpodcaster.android.domain.model.AdminUser selectedUser = null;
    private final boolean showEditDialog = false;
    private final boolean showCreateDialog = false;
    private final boolean showDeleteConfirm = false;
    private final boolean showResetPasswordDialog = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String dialogEmail = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String dialogPlan = null;
    private final int dialogQuota = 0;
    private final boolean dialogIsAdmin = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String dialogPassword = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String dialogSuccess = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String dialogError = null;
    private final boolean isDialogLoading = false;
    
    public AdminState(@org.jetbrains.annotations.Nullable()
    com.pdfpodcaster.android.domain.model.AdminStats stats, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pdfpodcaster.android.domain.model.AdminUser> users, boolean isLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.Nullable()
    com.pdfpodcaster.android.domain.model.AdminUser selectedUser, boolean showEditDialog, boolean showCreateDialog, boolean showDeleteConfirm, boolean showResetPasswordDialog, @org.jetbrains.annotations.NotNull()
    java.lang.String dialogEmail, @org.jetbrains.annotations.NotNull()
    java.lang.String dialogPlan, int dialogQuota, boolean dialogIsAdmin, @org.jetbrains.annotations.NotNull()
    java.lang.String dialogPassword, @org.jetbrains.annotations.Nullable()
    java.lang.String dialogSuccess, @org.jetbrains.annotations.Nullable()
    java.lang.String dialogError, boolean isDialogLoading) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.pdfpodcaster.android.domain.model.AdminStats getStats() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.pdfpodcaster.android.domain.model.AdminUser> getUsers() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.pdfpodcaster.android.domain.model.AdminUser getSelectedUser() {
        return null;
    }
    
    public final boolean getShowEditDialog() {
        return false;
    }
    
    public final boolean getShowCreateDialog() {
        return false;
    }
    
    public final boolean getShowDeleteConfirm() {
        return false;
    }
    
    public final boolean getShowResetPasswordDialog() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDialogEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDialogPlan() {
        return null;
    }
    
    public final int getDialogQuota() {
        return 0;
    }
    
    public final boolean getDialogIsAdmin() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDialogPassword() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDialogSuccess() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDialogError() {
        return null;
    }
    
    public final boolean isDialogLoading() {
        return false;
    }
    
    public AdminState() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.pdfpodcaster.android.domain.model.AdminStats component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    public final int component12() {
        return 0;
    }
    
    public final boolean component13() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component16() {
        return null;
    }
    
    public final boolean component17() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.pdfpodcaster.android.domain.model.AdminUser> component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.pdfpodcaster.android.domain.model.AdminUser component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pdfpodcaster.android.ui.screens.admin.AdminState copy(@org.jetbrains.annotations.Nullable()
    com.pdfpodcaster.android.domain.model.AdminStats stats, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pdfpodcaster.android.domain.model.AdminUser> users, boolean isLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.Nullable()
    com.pdfpodcaster.android.domain.model.AdminUser selectedUser, boolean showEditDialog, boolean showCreateDialog, boolean showDeleteConfirm, boolean showResetPasswordDialog, @org.jetbrains.annotations.NotNull()
    java.lang.String dialogEmail, @org.jetbrains.annotations.NotNull()
    java.lang.String dialogPlan, int dialogQuota, boolean dialogIsAdmin, @org.jetbrains.annotations.NotNull()
    java.lang.String dialogPassword, @org.jetbrains.annotations.Nullable()
    java.lang.String dialogSuccess, @org.jetbrains.annotations.Nullable()
    java.lang.String dialogError, boolean isDialogLoading) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}