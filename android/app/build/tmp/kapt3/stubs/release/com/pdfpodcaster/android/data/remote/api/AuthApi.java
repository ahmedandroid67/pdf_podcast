package com.pdfpodcaster.android.data.remote.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u000e\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\r\u001a\u00020\u000e2\b\b\u0001\u0010\u0004\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\u00020\f2\b\b\u0001\u0010\u0004\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0013\u00a8\u0006\u0014"}, d2 = {"Lcom/pdfpodcaster/android/data/remote/api/AuthApi;", "", "changePassword", "Lcom/pdfpodcaster/android/data/remote/dto/MessageResponse;", "request", "Lcom/pdfpodcaster/android/data/remote/dto/ChangePasswordRequest;", "(Lcom/pdfpodcaster/android/data/remote/dto/ChangePasswordRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGoogleAuthUrl", "Lretrofit2/Response;", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMe", "Lcom/pdfpodcaster/android/data/remote/dto/UserDto;", "login", "Lcom/pdfpodcaster/android/data/remote/dto/TokenResponse;", "Lcom/pdfpodcaster/android/data/remote/dto/LoginRequest;", "(Lcom/pdfpodcaster/android/data/remote/dto/LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "register", "Lcom/pdfpodcaster/android/data/remote/dto/RegisterRequest;", "(Lcom/pdfpodcaster/android/data/remote/dto/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
public abstract interface AuthApi {
    
    @retrofit2.http.POST(value = "auth/login")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object login(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.remote.dto.LoginRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.TokenResponse> $completion);
    
    @retrofit2.http.POST(value = "auth/register")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object register(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.remote.dto.RegisterRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.UserDto> $completion);
    
    @retrofit2.http.GET(value = "auth/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMe(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.UserDto> $completion);
    
    @retrofit2.http.POST(value = "auth/change-password")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object changePassword(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.remote.dto.ChangePasswordRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.MessageResponse> $completion);
    
    @retrofit2.http.GET(value = "auth/google")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGoogleAuthUrl(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
}