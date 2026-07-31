package com.pdfpodcaster.android.data.repository;

import com.pdfpodcaster.android.data.remote.api.JobApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class JobRepositoryImpl_Factory implements Factory<JobRepositoryImpl> {
  private final Provider<JobApi> jobApiProvider;

  public JobRepositoryImpl_Factory(Provider<JobApi> jobApiProvider) {
    this.jobApiProvider = jobApiProvider;
  }

  @Override
  public JobRepositoryImpl get() {
    return newInstance(jobApiProvider.get());
  }

  public static JobRepositoryImpl_Factory create(Provider<JobApi> jobApiProvider) {
    return new JobRepositoryImpl_Factory(jobApiProvider);
  }

  public static JobRepositoryImpl newInstance(JobApi jobApi) {
    return new JobRepositoryImpl(jobApi);
  }
}
