package com.pdfpodcaster.android.di;

import com.pdfpodcaster.android.data.remote.api.PipelineApi;
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
public final class AppModule_ProvidePipelineApiFactory implements Factory<PipelineApi> {
  private final Provider<Retrofit> retrofitProvider;

  public AppModule_ProvidePipelineApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public PipelineApi get() {
    return providePipelineApi(retrofitProvider.get());
  }

  public static AppModule_ProvidePipelineApiFactory create(Provider<Retrofit> retrofitProvider) {
    return new AppModule_ProvidePipelineApiFactory(retrofitProvider);
  }

  public static PipelineApi providePipelineApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.providePipelineApi(retrofit));
  }
}
