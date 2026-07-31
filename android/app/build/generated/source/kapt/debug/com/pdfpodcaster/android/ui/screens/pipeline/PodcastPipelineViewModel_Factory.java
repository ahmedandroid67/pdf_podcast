package com.pdfpodcaster.android.ui.screens.pipeline;

import com.pdfpodcaster.android.domain.usecase.GetJobResultUseCase;
import com.pdfpodcaster.android.domain.usecase.PollJobProgressUseCase;
import com.pdfpodcaster.android.domain.usecase.ProcessUploadUseCase;
import com.pdfpodcaster.android.domain.usecase.ProcessUrlUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class PodcastPipelineViewModel_Factory implements Factory<PodcastPipelineViewModel> {
  private final Provider<ProcessUploadUseCase> processUploadUseCaseProvider;

  private final Provider<ProcessUrlUseCase> processUrlUseCaseProvider;

  private final Provider<PollJobProgressUseCase> pollJobProgressUseCaseProvider;

  private final Provider<GetJobResultUseCase> getJobResultUseCaseProvider;

  public PodcastPipelineViewModel_Factory(
      Provider<ProcessUploadUseCase> processUploadUseCaseProvider,
      Provider<ProcessUrlUseCase> processUrlUseCaseProvider,
      Provider<PollJobProgressUseCase> pollJobProgressUseCaseProvider,
      Provider<GetJobResultUseCase> getJobResultUseCaseProvider) {
    this.processUploadUseCaseProvider = processUploadUseCaseProvider;
    this.processUrlUseCaseProvider = processUrlUseCaseProvider;
    this.pollJobProgressUseCaseProvider = pollJobProgressUseCaseProvider;
    this.getJobResultUseCaseProvider = getJobResultUseCaseProvider;
  }

  @Override
  public PodcastPipelineViewModel get() {
    return newInstance(processUploadUseCaseProvider.get(), processUrlUseCaseProvider.get(), pollJobProgressUseCaseProvider.get(), getJobResultUseCaseProvider.get());
  }

  public static PodcastPipelineViewModel_Factory create(
      Provider<ProcessUploadUseCase> processUploadUseCaseProvider,
      Provider<ProcessUrlUseCase> processUrlUseCaseProvider,
      Provider<PollJobProgressUseCase> pollJobProgressUseCaseProvider,
      Provider<GetJobResultUseCase> getJobResultUseCaseProvider) {
    return new PodcastPipelineViewModel_Factory(processUploadUseCaseProvider, processUrlUseCaseProvider, pollJobProgressUseCaseProvider, getJobResultUseCaseProvider);
  }

  public static PodcastPipelineViewModel newInstance(ProcessUploadUseCase processUploadUseCase,
      ProcessUrlUseCase processUrlUseCase, PollJobProgressUseCase pollJobProgressUseCase,
      GetJobResultUseCase getJobResultUseCase) {
    return new PodcastPipelineViewModel(processUploadUseCase, processUrlUseCase, pollJobProgressUseCase, getJobResultUseCase);
  }
}
