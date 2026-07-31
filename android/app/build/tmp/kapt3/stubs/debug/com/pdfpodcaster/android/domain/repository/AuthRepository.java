package com.pdfpodcaster.android.domain.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J$\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0004H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0003H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0010\u0010\f\u001a\u0004\u0018\u00010\tH\u00a6@\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\r\u001a\u00020\u000eH\u00a6@\u00a2\u0006\u0002\u0010\u000bJ,\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0004H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u000e\u0010\u0014\u001a\u00020\u0015H\u00a6@\u00a2\u0006\u0002\u0010\u000bJ,\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0004H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0013\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0018"}, d2 = {"Lcom/pdfpodcaster/android/domain/repository/AuthRepository;", "", "changePassword", "Lkotlin/Result;", "", "newPassword", "changePassword-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMe", "Lcom/pdfpodcaster/android/domain/model/User;", "getMe-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSavedUser", "isLoggedIn", "", "login", "email", "password", "login-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "", "register", "register-0E7RQCE", "app_debug"})
public abstract interface AuthRepository {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object isLoggedIn(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSavedUser(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.domain.model.User> $completion);
}