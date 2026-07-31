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
public final class GetAdminStatsUseCase_Factory implements Factory<GetAdminStatsUseCase> {
  private final Provider<AdminRepository> adminRepositoryProvider;

  public GetAdminStatsUseCase_Factory(Provider<AdminRepository> adminRepositoryProvider) {
    this.adminRepositoryProvider = adminRepositoryProvider;
  }

  @Override
  public GetAdminStatsUseCase get() {
    return newInstance(adminRepositoryProvider.get());
  }

  public static GetAdminStatsUseCase_Factory create(
      Provider<AdminRepository> adminRepositoryProvider) {
    return new GetAdminStatsUseCase_Factory(adminRepositoryProvider);
  }

  public static GetAdminStatsUseCase newInstance(AdminRepository adminRepository) {
    return new GetAdminStatsUseCase(adminRepository);
  }
}
