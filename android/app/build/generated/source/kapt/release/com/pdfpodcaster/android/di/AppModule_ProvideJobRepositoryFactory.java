package com.pdfpodcaster.android.di;

import com.pdfpodcaster.android.data.repository.JobRepositoryImpl;
import com.pdfpodcaster.android.domain.repository.JobRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideJobRepositoryFactory implements Factory<JobRepository> {
  private final Provider<JobRepositoryImpl> implProvider;

  public AppModule_ProvideJobRepositoryFactory(Provider<JobRepositoryImpl> implProvider) {
    this.implProvider = implProvider;
  }

  @Override
  public JobRepository get() {
    return provideJobRepository(implProvider.get());
  }

  public static AppModule_ProvideJobRepositoryFactory create(
      Provider<JobRepositoryImpl> implProvider) {
    return new AppModule_ProvideJobRepositoryFactory(implProvider);
  }

  public static JobRepository provideJobRepository(JobRepositoryImpl impl) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideJobRepository(impl));
  }
}
