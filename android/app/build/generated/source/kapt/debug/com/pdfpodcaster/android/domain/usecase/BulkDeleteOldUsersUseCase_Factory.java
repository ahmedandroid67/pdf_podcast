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
public final class BulkDeleteOldUsersUseCase_Factory implements Factory<BulkDeleteOldUsersUseCase> {
  private final Provider<AdminRepository> adminRepositoryProvider;

  public BulkDeleteOldUsersUseCase_Factory(Provider<AdminRepository> adminRepositoryProvider) {
    this.adminRepositoryProvider = adminRepositoryProvider;
  }

  @Override
  public BulkDeleteOldUsersUseCase get() {
    return newInstance(adminRepositoryProvider.get());
  }

  public static BulkDeleteOldUsersUseCase_Factory create(
      Provider<AdminRepository> adminRepositoryProvider) {
    return new BulkDeleteOldUsersUseCase_Factory(adminRepositoryProvider);
  }

  public static BulkDeleteOldUsersUseCase newInstance(AdminRepository adminRepository) {
    return new BulkDeleteOldUsersUseCase(adminRepository);
  }
}
