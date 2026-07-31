package com.pdfpodcaster.android.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\bH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u000eH\u0096@\u00a2\u0006\u0002\u0010\u0010J\u000e\u0010\u0012\u001a\u00020\u0013H\u0096@\u00a2\u0006\u0002\u0010\u0010J,\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\b2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u000e\u0010\u0019\u001a\u00020\u001aH\u0096@\u00a2\u0006\u0002\u0010\u0010J,\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000e0\b2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001c\u0010\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001d"}, d2 = {"Lcom/pdfpodcaster/android/data/repository/AuthRepositoryImpl;", "Lcom/pdfpodcaster/android/domain/repository/AuthRepository;", "authApi", "Lcom/pdfpodcaster/android/data/remote/api/AuthApi;", "tokenDataStore", "Lcom/pdfpodcaster/android/data/local/TokenDataStore;", "(Lcom/pdfpodcaster/android/data/remote/api/AuthApi;Lcom/pdfpodcaster/android/data/local/TokenDataStore;)V", "changePassword", "Lkotlin/Result;", "", "newPassword", "changePassword-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMe", "Lcom/pdfpodcaster/android/domain/model/User;", "getMe-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSavedUser", "isLoggedIn", "", "login", "email", "password", "login-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "", "register", "register-0E7RQCE", "app_release"})
public final class AuthRepositoryImpl implements com.pdfpodcaster.android.domain.repository.AuthRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.data.remote.api.AuthApi authApi = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.data.local.TokenDataStore tokenDataStore = null;
    
    @javax.inject.Inject()
    public AuthRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.remote.api.AuthApi authApi, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.local.TokenDataStore tokenDataStore) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object isLoggedIn(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getSavedUser(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.domain.model.User> $completion) {
        return null;
    }
}