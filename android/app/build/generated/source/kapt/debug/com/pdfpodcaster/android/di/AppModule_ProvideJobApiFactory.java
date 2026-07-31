package com.pdfpodcaster.android.di;

import com.pdfpodcaster.android.data.remote.api.JobApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class AppModule_ProvideJobApiFactory implements Factory<JobApi> {
  private final Provider<Retrofit> retrofitProvider;

  public AppModule_ProvideJobApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public JobApi get() {
    return provideJobApi(retrofitProvider.get());
  }

  public static AppModule_ProvideJobApiFactory create(Provider<Retrofit> retrofitProvider) {
    return new AppModule_ProvideJobApiFactory(retrofitProvider);
  }

  public static JobApi provideJobApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideJobApi(retrofit));
  }
}
