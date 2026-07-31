package com.pdfpodcaster.android.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u0011H\u0007J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\u0016H\u0007J\b\u0010\u0017\u001a\u00020\u0018H\u0007J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010!\u001a\u00020\b2\u0006\u0010\"\u001a\u00020\u001a2\u0006\u0010#\u001a\u00020\u0018H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/pdfpodcaster/android/di/AppModule;", "", "()V", "BASE_URL", "", "provideAdminApi", "Lcom/pdfpodcaster/android/data/remote/api/AdminApi;", "retrofit", "Lretrofit2/Retrofit;", "provideAdminRepository", "Lcom/pdfpodcaster/android/domain/repository/AdminRepository;", "impl", "Lcom/pdfpodcaster/android/data/repository/AdminRepositoryImpl;", "provideAuthApi", "Lcom/pdfpodcaster/android/data/remote/api/AuthApi;", "provideAuthRepository", "Lcom/pdfpodcaster/android/domain/repository/AuthRepository;", "Lcom/pdfpodcaster/android/data/repository/AuthRepositoryImpl;", "provideJobApi", "Lcom/pdfpodcaster/android/data/remote/api/JobApi;", "provideJobRepository", "Lcom/pdfpodcaster/android/domain/repository/JobRepository;", "Lcom/pdfpodcaster/android/data/repository/JobRepositoryImpl;", "provideJson", "Lkotlinx/serialization/json/Json;", "provideOkHttpClient", "Lokhttp3/OkHttpClient;", "authInterceptor", "Lcom/pdfpodcaster/android/data/remote/interceptor/AuthInterceptor;", "errorInterceptor", "Lcom/pdfpodcaster/android/data/remote/interceptor/ErrorInterceptor;", "providePipelineApi", "Lcom/pdfpodcaster/android/data/remote/api/PipelineApi;", "provideRetrofit", "okHttpClient", "json", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class AppModule {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String BASE_URL = "http://10.0.2.2:8000";
    @org.jetbrains.annotations.NotNull()
    public static final com.pdfpodcaster.android.di.AppModule INSTANCE = null;
    
    private AppModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.serialization.json.Json provideJson() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final okhttp3.OkHttpClient provideOkHttpClient(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.remote.interceptor.AuthInterceptor authInterceptor, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.remote.interceptor.ErrorInterceptor errorInterceptor) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final retrofit2.Retrofit provideRetrofit(@org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient okHttpClient, @org.jetbrains.annotations.NotNull()
    kotlinx.serialization.json.Json json) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.pdfpodcaster.android.data.remote.api.AuthApi provideAuthApi(@org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.pdfpodcaster.android.data.remote.api.PipelineApi providePipelineApi(@org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.pdfpodcaster.android.data.remote.api.JobApi provideJobApi(@org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.pdfpodcaster.android.data.remote.api.AdminApi provideAdminApi(@org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.pdfpodcaster.android.domain.repository.AuthRepository provideAuthRepository(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.repository.AuthRepositoryImpl impl) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.pdfpodcaster.android.domain.repository.JobRepository provideJobRepository(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.repository.JobRepositoryImpl impl) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.pdfpodcaster.android.domain.repository.AdminRepository provideAdminRepository(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.repository.AdminRepositoryImpl impl) {
        return null;
    }
}