package com.pdfpodcaster.android.data.remote.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\u0004\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u000b\u001a\u00020\f2\b\b\u0001\u0010\u0004\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0018\u0010\u000f\u001a\u00020\u00032\b\b\u0001\u0010\u0010\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0012\u00a8\u0006\u0013"}, d2 = {"Lcom/pdfpodcaster/android/data/remote/api/PipelineApi;", "", "fetchPdfUrl", "Lcom/pdfpodcaster/android/data/remote/dto/TextResponse;", "request", "Lcom/pdfpodcaster/android/data/remote/dto/UrlRequest;", "(Lcom/pdfpodcaster/android/data/remote/dto/UrlRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generatePodcast", "Lcom/pdfpodcaster/android/data/remote/dto/PodcastResponse;", "Lcom/pdfpodcaster/android/data/remote/dto/PodcastRequest;", "(Lcom/pdfpodcaster/android/data/remote/dto/PodcastRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "summarize", "Lcom/pdfpodcaster/android/data/remote/dto/SummaryResponse;", "Lcom/pdfpodcaster/android/data/remote/dto/SummaryRequest;", "(Lcom/pdfpodcaster/android/data/remote/dto/SummaryRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadPdf", "file", "Lokhttp3/MultipartBody$Part;", "(Lokhttp3/MultipartBody$Part;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
public abstract interface PipelineApi {
    
    @retrofit2.http.Multipart()
    @retrofit2.http.POST(value = "api/upload")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object uploadPdf(@retrofit2.http.Part()
    @org.jetbrains.annotations.NotNull()
    okhttp3.MultipartBody.Part file, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.TextResponse> $completion);
    
    @retrofit2.http.POST(value = "api/url")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object fetchPdfUrl(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.remote.dto.UrlRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.TextResponse> $completion);
    
    @retrofit2.http.POST(value = "api/summarize")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object summarize(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.remote.dto.SummaryRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.SummaryResponse> $completion);
    
    @retrofit2.http.POST(value = "api/podcast")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object generatePodcast(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.remote.dto.PodcastRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.pdfpodcaster.android.data.remote.dto.PodcastResponse> $completion);
}