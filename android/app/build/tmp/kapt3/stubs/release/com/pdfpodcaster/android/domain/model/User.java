package com.pdfpodcaster.android.domain.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\u0012\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\rJ\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0006H\u00c6\u0003J\t\u0010 \u001a\u00020\bH\u00c6\u0003J\t\u0010!\u001a\u00020\bH\u00c6\u0003J\t\u0010\"\u001a\u00020\u000bH\u00c6\u0003J\t\u0010#\u001a\u00020\u000bH\u00c6\u0003JO\u0010$\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000bH\u00c6\u0001J\u0013\u0010%\u001a\u00020\u000b2\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\'\u001a\u00020\bH\u00d6\u0001J\t\u0010(\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0013R\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0013R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u00178F\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u000fR\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u000f\u00a8\u0006)"}, d2 = {"Lcom/pdfpodcaster/android/domain/model/User;", "", "id", "", "email", "plan", "Lcom/pdfpodcaster/android/domain/model/PlanType;", "dailyQuota", "", "usedToday", "isActive", "", "isAdmin", "(Ljava/lang/String;Ljava/lang/String;Lcom/pdfpodcaster/android/domain/model/PlanType;IIZZ)V", "getDailyQuota", "()I", "getEmail", "()Ljava/lang/String;", "getId", "()Z", "getPlan", "()Lcom/pdfpodcaster/android/domain/model/PlanType;", "quotaPercentage", "", "getQuotaPercentage", "()F", "remainingQuota", "getRemainingQuota", "getUsedToday", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "toString", "app_release"})
public final class User {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String email = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.model.PlanType plan = null;
    private final int dailyQuota = 0;
    private final int usedToday = 0;
    private final boolean isActive = false;
    private final boolean isAdmin = false;
    
    public User(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.model.PlanType plan, int dailyQuota, int usedToday, boolean isActive, boolean isAdmin) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pdfpodcaster.android.domain.model.PlanType getPlan() {
        return null;
    }
    
    public final int getDailyQuota() {
        return 0;
    }
    
    public final int getUsedToday() {
        return 0;
    }
    
    public final boolean isActive() {
        return false;
    }
    
    public final boolean isAdmin() {
        return false;
    }
    
    public final int getRemainingQuota() {
        return 0;
    }
    
    public final float getQuotaPercentage() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pdfpodcaster.android.domain.model.PlanType component3() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pdfpodcaster.android.domain.model.User copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.model.PlanType plan, int dailyQuota, int usedToday, boolean isActive, boolean isAdmin) {
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