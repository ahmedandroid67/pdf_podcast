package com.pdfpodcaster.android.data.remote.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u00020\u000b2\b\b\u0001\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\"\u0010\u0013\u001a\u00020\u00142\b\b\u0001\u0010\f\u001a\u00020\r2\b\b\u0001\u0010\u0007\u001a\u00020\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u0018\u0010\u0017\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\"\u0010\u0018\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\r2\b\b\u0001\u0010\u0007\u001a\u00020\u0019H\u00a7@\u00a2\u0006\u0002\u0010\u001a\u00a8\u0006\u001b"}, d2 = {"Lcom/pdfpodcaster/android/data/remote/api/AdminApi;", "", "bulkDeleteOldUsers", "Lcom/pdfpodcaster/android/data/remote/dto/AdminBulkDeleteResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createUser", "Lcom/pdfpodcaster/android/data/remote/dto/AdminUserDto;", "request", "Lcom/pdfpodcaster/android/data/remote/dto/AdminUserCreateRequest;", "(Lcom/pdfpodcaster/android/data/remote/dto/AdminUserCreateRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteUser", "", "userId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStats", "Lcom/pdfpodcaster/android/data/remote/dto/AdminStatsDto;", "getUsers", "", "resetPassword", "Lcom/pdfpodcaster/android/data/remote/dto/MessageResponse;", "Lcom/pdfpodcaster/android/data/remote/dto/AdminResetPasswordRequest;", "(Ljava/lang/String;Lcom/pdfpodcaster/android/data/remote/dto/AdminResetPasswordRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toggleUserStatus", "updateUser", "Lcom/pdfpodcaster/android/data/remote/dto/AdminUserUpdateRequest;", "(Ljava/lang/String;Lcom/pdfpodcaster/android/data/remote/dto/AdminUserUpdateRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
public abstract interface AdminApi {
    
    @retrofit2.http.GET(value = "api/admin/stats")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getStats(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.AdminStatsDto> $completion);
    
    @retrofit2.http.GET(value = "api/admin/users")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUsers(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.pdfpodcaster.android.data.remote.dto.AdminUserDto>> $completion);
    
    @retrofit2.http.POST(value = "api/admin/users")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createUser(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.remote.dto.AdminUserCreateRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.AdminUserDto> $completion);
    
    @retrofit2.http.PUT(value = "api/admin/users/{userId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateUser(@retrofit2.http.Path(value = "userId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.remote.dto.AdminUserUpdateRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.AdminUserDto> $completion);
    
    @retrofit2.http.PUT(value = "api/admin/users/{userId}/status")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object toggleUserStatus(@retrofit2.http.Path(value = "userId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.AdminUserDto> $completion);
    
    @retrofit2.http.POST(value = "api/admin/users/{userId}/reset-password")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object resetPassword(@retrofit2.http.Path(value = "userId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.remote.dto.AdminResetPasswordRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.MessageResponse> $completion);
    
    @retrofit2.http.DELETE(value = "api/admin/users/{userId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteUser(@retrofit2.http.Path(value = "userId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @retrofit2.http.DELETE(value = "api/admin/users/bulk/old")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object bulkDeleteOldUsers(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.AdminBulkDeleteResponse> $completion);
}