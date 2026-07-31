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
public final class UpdateAdminUserUseCase_Factory implements Factory<UpdateAdminUserUseCase> {
  private final Provider<AdminRepository> adminRepositoryProvider;

  public UpdateAdminUserUseCase_Factory(Provider<AdminRepository> adminRepositoryProvider) {
    this.adminRepositoryProvider = adminRepositoryProvider;
  }

  @Override
  public UpdateAdminUserUseCase get() {
    return newInstance(adminRepositoryProvider.get());
  }

  public static UpdateAdminUserUseCase_Factory create(
      Provider<AdminRepository> adminRepositoryProvider) {
    return new UpdateAdminUserUseCase_Factory(adminRepositoryProvider);
  }

  public static UpdateAdminUserUseCase newInstance(AdminRepository adminRepository) {
    return new UpdateAdminUserUseCase(adminRepository);
  }
}
