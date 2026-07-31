package com.pdfpodcaster.android.di;

import com.pdfpodcaster.android.data.remote.interceptor.AuthInterceptor;
import com.pdfpodcaster.android.data.remote.interceptor.ErrorInterceptor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

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
public final class AppModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final Provider<AuthInterceptor> authInterceptorProvider;

  private final Provider<ErrorInterceptor> errorInterceptorProvider;

  public AppModule_ProvideOkHttpClientFactory(Provider<AuthInterceptor> authInterceptorProvider,
      Provider<ErrorInterceptor> errorInterceptorProvider) {
    this.authInterceptorProvider = authInterceptorProvider;
    this.errorInterceptorProvider = errorInterceptorProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOkHttpClient(authInterceptorProvider.get(), errorInterceptorProvider.get());
  }

  public static AppModule_ProvideOkHttpClientFactory create(
      Provider<AuthInterceptor> authInterceptorProvider,
      Provider<ErrorInterceptor> errorInterceptorProvider) {
    return new AppModule_ProvideOkHttpClientFactory(authInterceptorProvider, errorInterceptorProvider);
  }

  public static OkHttpClient provideOkHttpClient(AuthInterceptor authInterceptor,
      ErrorInterceptor errorInterceptor) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideOkHttpClient(authInterceptor, errorInterceptor));
  }
}
