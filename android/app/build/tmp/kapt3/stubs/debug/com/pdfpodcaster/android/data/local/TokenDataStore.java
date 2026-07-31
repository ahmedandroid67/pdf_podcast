package com.pdfpodcaster.android.data.local;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0007\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\f\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/pdfpodcaster/android/data/local/TokenDataStore;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "masterKey", "Landroidx/security/crypto/MasterKey;", "prefs", "Landroid/content/SharedPreferences;", "clearAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearToken", "getToken", "", "getUserEmail", "getUserPlan", "saveToken", "token", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveUserEmail", "email", "saveUserPlan", "plan", "Companion", "app_debug"})
public final class TokenDataStore {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.security.crypto.MasterKey masterKey = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_TOKEN = "auth_token";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_USER_EMAIL = "user_email";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_USER_PLAN = "user_plan";
    @org.jetbrains.annotations.NotNull()
    public static final com.pdfpodcaster.android.data.local.TokenDataStore.Companion Companion = null;
    
    @javax.inject.Inject()
    public TokenDataStore(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getToken(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearToken(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveUserEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserEmail(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveUserPlan(@org.jetbrains.annotations.NotNull()
    java.lang.String plan, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserPlan(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/pdfpodcaster/android/data/local/TokenDataStore$Companion;", "", "()V", "KEY_TOKEN", "", "KEY_USER_EMAIL", "KEY_USER_PLAN", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}