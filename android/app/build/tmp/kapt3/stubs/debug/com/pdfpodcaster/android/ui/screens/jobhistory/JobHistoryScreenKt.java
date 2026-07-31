package com.pdfpodcaster.android.ui.screens.jobhistory;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001aQ\u0010\u0006\u001a\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052!\u0010\b\u001a\u001d\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0007\u001aH\u0010\u0011\u001a\u00020\u00012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00030\u00132\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\t2\u0006\u0010\u0017\u001a\u00020\u0018H\u0003\u001a\u0010\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001bH\u0003\u001a\u0010\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001bH\u0003\u00a8\u0006\u001d"}, d2 = {"JobCard", "", "job", "Lcom/pdfpodcaster/android/domain/model/PodcastJob;", "onClick", "Lkotlin/Function0;", "JobHistoryScreen", "onBack", "onJobClick", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "jobId", "onRetryJob", "viewModel", "Lcom/pdfpodcaster/android/ui/screens/jobhistory/JobHistoryViewModel;", "RefreshingList", "jobs", "", "isRefreshing", "", "onRefresh", "paddingValues", "Landroidx/compose/foundation/layout/PaddingValues;", "StatusChip", "status", "Lcom/pdfpodcaster/android/domain/model/JobStatus;", "StatusIcon", "app_debug"})
public final class JobHistoryScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void JobHistoryScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onJobClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRetryJob, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.ui.screens.jobhistory.JobHistoryViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void RefreshingList(java.util.List<com.pdfpodcaster.android.domain.model.PodcastJob> jobs, boolean isRefreshing, kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onJobClick, androidx.compose.foundation.layout.PaddingValues paddingValues) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void JobCard(com.pdfpodcaster.android.domain.model.PodcastJob job, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatusIcon(com.pdfpodcaster.android.domain.model.JobStatus status) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatusChip(com.pdfpodcaster.android.domain.model.JobStatus status) {
    }
}