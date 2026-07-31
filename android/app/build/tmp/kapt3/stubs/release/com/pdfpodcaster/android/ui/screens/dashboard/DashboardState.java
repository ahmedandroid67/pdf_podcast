package com.pdfpodcaster.android.ui.screens.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u001c\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bm\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\b\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\u0010J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\bH\u00c6\u0003J\t\u0010 \u001a\u00020\bH\u00c6\u0003J\t\u0010!\u001a\u00020\fH\u00c6\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\fH\u00c6\u0003Jq\u0010%\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\fH\u00c6\u0001J\u0013\u0010&\u001a\u00020\b2\b\u0010\'\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010(\u001a\u00020)H\u00d6\u0001J\t\u0010*\u001a\u00020\fH\u00d6\u0001R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0013R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u0013\u0010\r\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006+"}, d2 = {"Lcom/pdfpodcaster/android/ui/screens/dashboard/DashboardState;", "", "user", "Lcom/pdfpodcaster/android/domain/model/User;", "jobs", "", "Lcom/pdfpodcaster/android/domain/model/PodcastJob;", "isLoading", "", "isChangingPassword", "showChangePasswordDialog", "newPassword", "", "passwordChangeSuccess", "passwordChangeError", "error", "(Lcom/pdfpodcaster/android/domain/model/User;Ljava/util/List;ZZZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getError", "()Ljava/lang/String;", "()Z", "getJobs", "()Ljava/util/List;", "getNewPassword", "getPasswordChangeError", "getPasswordChangeSuccess", "getShowChangePasswordDialog", "getUser", "()Lcom/pdfpodcaster/android/domain/model/User;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_release"})
public final class DashboardState {
    @org.jetbrains.annotations.Nullable()
    private final com.pdfpodcaster.android.domain.model.User user = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.pdfpodcaster.android.domain.model.PodcastJob> jobs = null;
    private final boolean isLoading = false;
    private final boolean isChangingPassword = false;
    private final boolean showChangePasswordDialog = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String newPassword = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String passwordChangeSuccess = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String passwordChangeError = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    
    public DashboardState(@org.jetbrains.annotations.Nullable()
    com.pdfpodcaster.android.domain.model.User user, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pdfpodcaster.android.domain.model.PodcastJob> jobs, boolean isLoading, boolean isChangingPassword, boolean showChangePasswordDialog, @org.jetbrains.annotations.NotNull()
    java.lang.String newPassword, @org.jetbrains.annotations.Nullable()
    java.lang.String passwordChangeSuccess, @org.jetbrains.annotations.Nullable()
    java.lang.String passwordChangeError, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.pdfpodcaster.android.domain.model.User getUser() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.pdfpodcaster.android.domain.model.PodcastJob> getJobs() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isChangingPassword() {
        return false;
    }
    
    public final boolean getShowChangePasswordDialog() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNewPassword() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPasswordChangeSuccess() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPasswordChangeError() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public DashboardState() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.pdfpodcaster.android.domain.model.User component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.pdfpodcaster.android.domain.model.PodcastJob> component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pdfpodcaster.android.ui.screens.dashboard.DashboardState copy(@org.jetbrains.annotations.Nullable()
    com.pdfpodcaster.android.domain.model.User user, @org.jetbrains.annotations.NotNull()
    java.util.List<com.pdfpodcaster.android.domain.model.PodcastJob> jobs, boolean isLoading, boolean isChangingPassword, boolean showChangePasswordDialog, @org.jetbrains.annotations.NotNull()
    java.lang.String newPassword, @org.jetbrains.annotations.Nullable()
    java.lang.String passwordChangeSuccess, @org.jetbrains.annotations.Nullable()
    java.lang.String passwordChangeError, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
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