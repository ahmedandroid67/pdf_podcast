package com.pdfpodcaster.android.data.remote.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ,\u0010\u000e\u001a\u00020\u000f2\b\b\u0001\u0010\u0010\u001a\u00020\u00112\b\b\u0001\u0010\u0012\u001a\u00020\u00052\b\b\u0001\u0010\u0013\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u0018\u0010\u0015\u001a\u00020\u000f2\b\b\u0001\u0010\u0016\u001a\u00020\u0017H\u00a7@\u00a2\u0006\u0002\u0010\u0018\u00a8\u0006\u0019"}, d2 = {"Lcom/pdfpodcaster/android/data/remote/api/JobApi;", "", "getJob", "Lcom/pdfpodcaster/android/data/remote/dto/JobDto;", "jobId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getJobProgress", "Lcom/pdfpodcaster/android/data/remote/dto/JobProgressDto;", "getJobResult", "Lcom/pdfpodcaster/android/data/remote/dto/JobResultDto;", "getJobs", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processUpload", "Lcom/pdfpodcaster/android/data/remote/dto/JobSubmitResponse;", "file", "Lokhttp3/MultipartBody$Part;", "lang", "mode", "(Lokhttp3/MultipartBody$Part;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processUrl", "request", "Lcom/pdfpodcaster/android/data/remote/dto/UrlRequest;", "(Lcom/pdfpodcaster/android/data/remote/dto/UrlRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface JobApi {
    
    @retrofit2.http.Multipart()
    @retrofit2.http.POST(value = "api/process/upload")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object processUpload(@retrofit2.http.Part()
    @org.jetbrains.annotations.NotNull()
    okhttp3.MultipartBody.Part file, @retrofit2.http.Part(value = "lang")
    @org.jetbrains.annotations.NotNull()
    java.lang.String lang, @retrofit2.http.Part(value = "mode")
    @org.jetbrains.annotations.NotNull()
    java.lang.String mode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.JobSubmitResponse> $completion);
    
    @retrofit2.http.POST(value = "api/process/url")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object processUrl(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.remote.dto.UrlRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.JobSubmitResponse> $completion);
    
    @retrofit2.http.GET(value = "api/jobs")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getJobs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.pdfpodcaster.android.data.remote.dto.JobDto>> $completion);
    
    @retrofit2.http.GET(value = "api/jobs/{jobId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getJob(@retrofit2.http.Path(value = "jobId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.JobDto> $completion);
    
    @retrofit2.http.GET(value = "api/jobs/{jobId}/progress")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getJobProgress(@retrofit2.http.Path(value = "jobId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.JobProgressDto> $completion);
    
    @retrofit2.http.GET(value = "api/jobs/{jobId}/result")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getJobResult(@retrofit2.http.Path(value = "jobId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.JobResultDto> $completion);
}