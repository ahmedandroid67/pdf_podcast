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
public final class ProcessUrlUseCase_Factory implements Factory<ProcessUrlUseCase> {
  private final Provider<JobRepository> jobRepositoryProvider;

  public ProcessUrlUseCase_Factory(Provider<JobRepository> jobRepositoryProvider) {
    this.jobRepositoryProvider = jobRepositoryProvider;
  }

  @Override
  public ProcessUrlUseCase get() {
    return newInstance(jobRepositoryProvider.get());
  }

  public static ProcessUrlUseCase_Factory create(Provider<JobRepository> jobRepositoryProvider) {
    return new ProcessUrlUseCase_Factory(jobRepositoryProvider);
  }

  public static ProcessUrlUseCase newInstance(JobRepository jobRepository) {
    return new ProcessUrlUseCase(jobRepository);
  }
}
