package com.pdfpodcaster.android.domain.usecase;

import com.pdfpodcaster.android.domain.repository.JobRepository;
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
public final class GetJobsUseCase_Factory implements Factory<GetJobsUseCase> {
  private final Provider<JobRepository> jobRepositoryProvider;

  public GetJobsUseCase_Factory(Provider<JobRepository> jobRepositoryProvider) {
    this.jobRepositoryProvider = jobRepositoryProvider;
  }

  @Override
  public GetJobsUseCase get() {
    return newInstance(jobRepositoryProvider.get());
  }

  public static GetJobsUseCase_Factory create(Provider<JobRepository> jobRepositoryProvider) {
    return new GetJobsUseCase_Factory(jobRepositoryProvider);
  }

  public static GetJobsUseCase newInstance(JobRepository jobRepository) {
    return new GetJobsUseCase(jobRepository);
  }
}
