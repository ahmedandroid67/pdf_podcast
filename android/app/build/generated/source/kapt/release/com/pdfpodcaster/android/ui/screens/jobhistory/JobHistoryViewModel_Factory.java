package com.pdfpodcaster.android.ui.screens.jobhistory;

import com.pdfpodcaster.android.domain.usecase.GetJobsUseCase;
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
public final class JobHistoryViewModel_Factory implements Factory<JobHistoryViewModel> {
  private final Provider<GetJobsUseCase> getJobsUseCaseProvider;

  public JobHistoryViewModel_Factory(Provider<GetJobsUseCase> getJobsUseCaseProvider) {
    this.getJobsUseCaseProvider = getJobsUseCaseProvider;
  }

  @Override
  public JobHistoryViewModel get() {
    return newInstance(getJobsUseCaseProvider.get());
  }

  public static JobHistoryViewModel_Factory create(
      Provider<GetJobsUseCase> getJobsUseCaseProvider) {
    return new JobHistoryViewModel_Factory(getJobsUseCaseProvider);
  }

  public static JobHistoryViewModel newInstance(GetJobsUseCase getJobsUseCase) {
    return new JobHistoryViewModel(getJobsUseCase);
  }
}
