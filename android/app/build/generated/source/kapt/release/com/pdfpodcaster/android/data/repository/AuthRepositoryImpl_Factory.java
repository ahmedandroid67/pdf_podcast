package com.pdfpodcaster.android.data.repository;

import com.pdfpodcaster.android.data.local.TokenDataStore;
import com.pdfpodcaster.android.data.remote.api.AuthApi;
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
public final class AuthRepositoryImpl_Factory implements Factory<AuthRepositoryImpl> {
  private final Provider<AuthApi> authApiProvider;

  private final Provider<TokenDataStore> tokenDataStoreProvider;

  public AuthRepositoryImpl_Factory(Provider<AuthApi> authApiProvider,
      Provider<TokenDataStore> tokenDataStoreProvider) {
    this.authApiProvider = authApiProvider;
    this.tokenDataStoreProvider = tokenDataStoreProvider;
  }

  @Override
  public AuthRepositoryImpl get() {
    return newInstance(authApiProvider.get(), tokenDataStoreProvider.get());
  }

  public static AuthRepositoryImpl_Factory create(Provider<AuthApi> authApiProvider,
      Provider<TokenDataStore> tokenDataStoreProvider) {
    return new AuthRepositoryImpl_Factory(authApiProvider, tokenDataStoreProvider);
  }

  public static AuthRepositoryImpl newInstance(AuthApi authApi, TokenDataStore tokenDataStore) {
    return new AuthRepositoryImpl(authApi, tokenDataStore);
  }
}
