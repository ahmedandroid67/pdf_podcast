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
public final class GetAdminUsersUseCase_Factory implements Factory<GetAdminUsersUseCase> {
  private final Provider<AdminRepository> adminRepositoryProvider;

  public GetAdminUsersUseCase_Factory(Provider<AdminRepository> adminRepositoryProvider) {
    this.adminRepositoryProvider = adminRepositoryProvider;
  }

  @Override
  public GetAdminUsersUseCase get() {
    return newInstance(adminRepositoryProvider.get());
  }

  public static GetAdminUsersUseCase_Factory create(
      Provider<AdminRepository> adminRepositoryProvider) {
    return new GetAdminUsersUseCase_Factory(adminRepositoryProvider);
  }

  public static GetAdminUsersUseCase newInstance(AdminRepository adminRepository) {
    return new GetAdminUsersUseCase(adminRepository);
  }
}
