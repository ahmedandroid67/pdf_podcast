package com.pdfpodcaster.android.ui.screens.admin;

import com.pdfpodcaster.android.domain.usecase.BulkDeleteOldUsersUseCase;
import com.pdfpodcaster.android.domain.usecase.CreateAdminUserUseCase;
import com.pdfpodcaster.android.domain.usecase.DeleteAdminUserUseCase;
import com.pdfpodcaster.android.domain.usecase.GetAdminStatsUseCase;
import com.pdfpodcaster.android.domain.usecase.GetAdminUsersUseCase;
import com.pdfpodcaster.android.domain.usecase.ResetAdminPasswordUseCase;
import com.pdfpodcaster.android.domain.usecase.ToggleAdminUserStatusUseCase;
import com.pdfpodcaster.android.domain.usecase.UpdateAdminUserUseCase;
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
public final class AdminDashboardViewModel_Factory implements Factory<AdminDashboardViewModel> {
  private final Provider<GetAdminStatsUseCase> getAdminStatsUseCaseProvider;

  private final Provider<GetAdminUsersUseCase> getAdminUsersUseCaseProvider;

  private final Provider<CreateAdminUserUseCase> createAdminUserUseCaseProvider;

  private final Provider<UpdateAdminUserUseCase> updateAdminUserUseCaseProvider;

  private final Provider<ToggleAdminUserStatusUseCase> toggleAdminUserStatusUseCaseProvider;

  private final Provider<ResetAdminPasswordUseCase> resetAdminPasswordUseCaseProvider;

  private final Provider<DeleteAdminUserUseCase> deleteAdminUserUseCaseProvider;

  private final Provider<BulkDeleteOldUsersUseCase> bulkDeleteOldUsersUseCaseProvider;

  public AdminDashboardViewModel_Factory(
      Provider<GetAdminStatsUseCase> getAdminStatsUseCaseProvider,
      Provider<GetAdminUsersUseCase> getAdminUsersUseCaseProvider,
      Provider<CreateAdminUserUseCase> createAdminUserUseCaseProvider,
      Provider<UpdateAdminUserUseCase> updateAdminUserUseCaseProvider,
      Provider<ToggleAdminUserStatusUseCase> toggleAdminUserStatusUseCaseProvider,
      Provider<ResetAdminPasswordUseCase> resetAdminPasswordUseCaseProvider,
      Provider<DeleteAdminUserUseCase> deleteAdminUserUseCaseProvider,
      Provider<BulkDeleteOldUsersUseCase> bulkDeleteOldUsersUseCaseProvider) {
    this.getAdminStatsUseCaseProvider = getAdminStatsUseCaseProvider;
    this.getAdminUsersUseCaseProvider = getAdminUsersUseCaseProvider;
    this.createAdminUserUseCaseProvider = createAdminUserUseCaseProvider;
    this.updateAdminUserUseCaseProvider = updateAdminUserUseCaseProvider;
    this.toggleAdminUserStatusUseCaseProvider = toggleAdminUserStatusUseCaseProvider;
    this.resetAdminPasswordUseCaseProvider = resetAdminPasswordUseCaseProvider;
    this.deleteAdminUserUseCaseProvider = deleteAdminUserUseCaseProvider;
    this.bulkDeleteOldUsersUseCaseProvider = bulkDeleteOldUsersUseCaseProvider;
  }

  @Override
  public AdminDashboardViewModel get() {
    return newInstance(getAdminStatsUseCaseProvider.get(), getAdminUsersUseCaseProvider.get(), createAdminUserUseCaseProvider.get(), updateAdminUserUseCaseProvider.get(), toggleAdminUserStatusUseCaseProvider.get(), resetAdminPasswordUseCaseProvider.get(), deleteAdminUserUseCaseProvider.get(), bulkDeleteOldUsersUseCaseProvider.get());
  }

  public static AdminDashboardViewModel_Factory create(
      Provider<GetAdminStatsUseCase> getAdminStatsUseCaseProvider,
      Provider<GetAdminUsersUseCase> getAdminUsersUseCaseProvider,
      Provider<CreateAdminUserUseCase> createAdminUserUseCaseProvider,
      Provider<UpdateAdminUserUseCase> updateAdminUserUseCaseProvider,
      Provider<ToggleAdminUserStatusUseCase> toggleAdminUserStatusUseCaseProvider,
      Provider<ResetAdminPasswordUseCase> resetAdminPasswordUseCaseProvider,
      Provider<DeleteAdminUserUseCase> deleteAdminUserUseCaseProvider,
      Provider<BulkDeleteOldUsersUseCase> bulkDeleteOldUsersUseCaseProvider) {
    return new AdminDashboardViewModel_Factory(getAdminStatsUseCaseProvider, getAdminUsersUseCaseProvider, createAdminUserUseCaseProvider, updateAdminUserUseCaseProvider, toggleAdminUserStatusUseCaseProvider, resetAdminPasswordUseCaseProvider, deleteAdminUserUseCaseProvider, bulkDeleteOldUsersUseCaseProvider);
  }

  public static AdminDashboardViewModel newInstance(GetAdminStatsUseCase getAdminStatsUseCase,
      GetAdminUsersUseCase getAdminUsersUseCase, CreateAdminUserUseCase createAdminUserUseCase,
      UpdateAdminUserUseCase updateAdminUserUseCase,
      ToggleAdminUserStatusUseCase toggleAdminUserStatusUseCase,
      ResetAdminPasswordUseCase resetAdminPasswordUseCase,
      DeleteAdminUserUseCase deleteAdminUserUseCase,
      BulkDeleteOldUsersUseCase bulkDeleteOldUsersUseCase) {
    return new AdminDashboardViewModel(getAdminStatsUseCase, getAdminUsersUseCase, createAdminUserUseCase, updateAdminUserUseCase, toggleAdminUserStatusUseCase, resetAdminPasswordUseCase, deleteAdminUserUseCase, bulkDeleteOldUsersUseCase);
  }
}
