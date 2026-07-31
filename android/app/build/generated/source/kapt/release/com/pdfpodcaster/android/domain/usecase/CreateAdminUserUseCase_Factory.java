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
public final class CreateAdminUserUseCase_Factory implements Factory<CreateAdminUserUseCase> {
  private final Provider<AdminRepository> adminRepositoryProvider;

  public CreateAdminUserUseCase_Factory(Provider<AdminRepository> adminRepositoryProvider) {
    this.adminRepositoryProvider = adminRepositoryProvider;
  }

  @Override
  public CreateAdminUserUseCase get() {
    return newInstance(adminRepositoryProvider.get());
  }

  public static CreateAdminUserUseCase_Factory create(
      Provider<AdminRepository> adminRepositoryProvider) {
    return new CreateAdminUserUseCase_Factory(adminRepositoryProvider);
  }

  public static CreateAdminUserUseCase newInstance(AdminRepository adminRepository) {
    return new CreateAdminUserUseCase(adminRepository);
  }
}
