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
public final class PollJobProgressUseCase_Factory implements Factory<PollJobProgressUseCase> {
  private final Provider<JobRepository> jobRepositoryProvider;

  public PollJobProgressUseCase_Factory(Provider<JobRepository> jobRepositoryProvider) {
    this.jobRepositoryProvider = jobRepositoryProvider;
  }

  @Override
  public PollJobProgressUseCase get() {
    return newInstance(jobRepositoryProvider.get());
  }

  public static PollJobProgressUseCase_Factory create(
      Provider<JobRepository> jobRepositoryProvider) {
    return new PollJobProgressUseCase_Factory(jobRepositoryProvider);
  }

  public static PollJobProgressUseCase newInstance(JobRepository jobRepository) {
    return new PollJobProgressUseCase(jobRepository);
  }
}
