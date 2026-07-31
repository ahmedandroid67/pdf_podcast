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
public final class ToggleAdminUserStatusUseCase_Factory implements Factory<ToggleAdminUserStatusUseCase> {
  private final Provider<AdminRepository> adminRepositoryProvider;

  public ToggleAdminUserStatusUseCase_Factory(Provider<AdminRepository> adminRepositoryProvider) {
    this.adminRepositoryProvider = adminRepositoryProvider;
  }

  @Override
  public ToggleAdminUserStatusUseCase get() {
    return newInstance(adminRepositoryProvider.get());
  }

  public static ToggleAdminUserStatusUseCase_Factory create(
      Provider<AdminRepository> adminRepositoryProvider) {
    return new ToggleAdminUserStatusUseCase_Factory(adminRepositoryProvider);
  }

  public static ToggleAdminUserStatusUseCase newInstance(AdminRepository adminRepository) {
    return new ToggleAdminUserStatusUseCase(adminRepository);
  }
}
