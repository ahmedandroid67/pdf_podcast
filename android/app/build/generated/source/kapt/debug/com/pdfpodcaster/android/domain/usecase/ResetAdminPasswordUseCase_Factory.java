package com.pdfpodcaster.android.domain.usecase;

import com.pdfpodcaster.android.domain.repository.AdminRepository;
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
public final class ResetAdminPasswordUseCase_Factory implements Factory<ResetAdminPasswordUseCase> {
  private final Provider<AdminRepository> adminRepositoryProvider;

  public ResetAdminPasswordUseCase_Factory(Provider<AdminRepository> adminRepositoryProvider) {
    this.adminRepositoryProvider = adminRepositoryProvider;
  }

  @Override
  public ResetAdminPasswordUseCase get() {
    return newInstance(adminRepositoryProvider.get());
  }

  public static ResetAdminPasswordUseCase_Factory create(
      Provider<AdminRepository> adminRepositoryProvider) {
    return new ResetAdminPasswordUseCase_Factory(adminRepositoryProvider);
  }

  public static ResetAdminPasswordUseCase newInstance(AdminRepository adminRepository) {
    return new ResetAdminPasswordUseCase(adminRepository);
  }
}
