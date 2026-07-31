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
public final class ProcessUploadUseCase_Factory implements Factory<ProcessUploadUseCase> {
  private final Provider<JobRepository> jobRepositoryProvider;

  public ProcessUploadUseCase_Factory(Provider<JobRepository> jobRepositoryProvider) {
    this.jobRepositoryProvider = jobRepositoryProvider;
  }

  @Override
  public ProcessUploadUseCase get() {
    return newInstance(jobRepositoryProvider.get());
  }

  public static ProcessUploadUseCase_Factory create(Provider<JobRepository> jobRepositoryProvider) {
    return new ProcessUploadUseCase_Factory(jobRepositoryProvider);
  }

  public static ProcessUploadUseCase newInstance(JobRepository jobRepository) {
    return new ProcessUploadUseCase(jobRepository);
  }
}
