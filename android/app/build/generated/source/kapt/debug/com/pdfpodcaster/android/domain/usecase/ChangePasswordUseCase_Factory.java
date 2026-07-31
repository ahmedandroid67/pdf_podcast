package com.pdfpodcaster.android.domain.usecase;

import com.pdfpodcaster.android.domain.repository.AuthRepository;
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
public final class ChangePasswordUseCase_Factory implements Factory<ChangePasswordUseCase> {
  private final Provider<AuthRepository> authRepositoryProvider;

  public ChangePasswordUseCase_Factory(Provider<AuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public ChangePasswordUseCase get() {
    return newInstance(authRepositoryProvider.get());
  }

  public static ChangePasswordUseCase_Factory create(
      Provider<AuthRepository> authRepositoryProvider) {
    return new ChangePasswordUseCase_Factory(authRepositoryProvider);
  }

  public static ChangePasswordUseCase newInstance(AuthRepository authRepository) {
    return new ChangePasswordUseCase(authRepository);
  }
}
