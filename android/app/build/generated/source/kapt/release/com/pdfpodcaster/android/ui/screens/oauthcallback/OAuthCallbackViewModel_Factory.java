package com.pdfpodcaster.android.ui.screens.oauthcallback;

import com.pdfpodcaster.android.data.local.TokenDataStore;
import com.pdfpodcaster.android.domain.usecase.LoginUseCase;
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
public final class OAuthCallbackViewModel_Factory implements Factory<OAuthCallbackViewModel> {
  private final Provider<TokenDataStore> tokenDataStoreProvider;

  private final Provider<LoginUseCase> loginUseCaseProvider;

  public OAuthCallbackViewModel_Factory(Provider<TokenDataStore> tokenDataStoreProvider,
      Provider<LoginUseCase> loginUseCaseProvider) {
    this.tokenDataStoreProvider = tokenDataStoreProvider;
    this.loginUseCaseProvider = loginUseCaseProvider;
  }

  @Override
  public OAuthCallbackViewModel get() {
    return newInstance(tokenDataStoreProvider.get(), loginUseCaseProvider.get());
  }

  public static OAuthCallbackViewModel_Factory create(
      Provider<TokenDataStore> tokenDataStoreProvider,
      Provider<LoginUseCase> loginUseCaseProvider) {
    return new OAuthCallbackViewModel_Factory(tokenDataStoreProvider, loginUseCaseProvider);
  }

  public static OAuthCallbackViewModel newInstance(TokenDataStore tokenDataStore,
      LoginUseCase loginUseCase) {
    return new OAuthCallbackViewModel(tokenDataStore, loginUseCase);
  }
}
