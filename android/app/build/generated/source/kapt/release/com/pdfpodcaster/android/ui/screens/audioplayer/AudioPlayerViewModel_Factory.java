package com.pdfpodcaster.android.ui.screens.audioplayer;

import android.content.Context;
import com.pdfpodcaster.android.data.local.TokenDataStore;
import com.pdfpodcaster.android.domain.usecase.GetJobResultUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AudioPlayerViewModel_Factory implements Factory<AudioPlayerViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<GetJobResultUseCase> getJobResultUseCaseProvider;

  private final Provider<TokenDataStore> tokenDataStoreProvider;

  public AudioPlayerViewModel_Factory(Provider<Context> contextProvider,
      Provider<GetJobResultUseCase> getJobResultUseCaseProvider,
      Provider<TokenDataStore> tokenDataStoreProvider) {
    this.contextProvider = contextProvider;
    this.getJobResultUseCaseProvider = getJobResultUseCaseProvider;
    this.tokenDataStoreProvider = tokenDataStoreProvider;
  }

  @Override
  public AudioPlayerViewModel get() {
    return newInstance(contextProvider.get(), getJobResultUseCaseProvider.get(), tokenDataStoreProvider.get());
  }

  public static AudioPlayerViewModel_Factory create(Provider<Context> contextProvider,
      Provider<GetJobResultUseCase> getJobResultUseCaseProvider,
      Provider<TokenDataStore> tokenDataStoreProvider) {
    return new AudioPlayerViewModel_Factory(contextProvider, getJobResultUseCaseProvider, tokenDataStoreProvider);
  }

  public static AudioPlayerViewModel newInstance(Context context,
      GetJobResultUseCase getJobResultUseCase, TokenDataStore tokenDataStore) {
    return new AudioPlayerViewModel(context, getJobResultUseCase, tokenDataStore);
  }
}
