package com.pdfpodcaster.android.ui.screens.dashboard;

import com.pdfpodcaster.android.domain.usecase.ChangePasswordUseCase;
import com.pdfpodcaster.android.domain.usecase.GetCurrentUserUseCase;
import com.pdfpodcaster.android.domain.usecase.GetJobsUseCase;
import com.pdfpodcaster.android.domain.usecase.LogoutUseCase;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<GetCurrentUserUseCase> getCurrentUserUseCaseProvider;

  private final Provider<LogoutUseCase> logoutUseCaseProvider;

  private final Provider<GetJobsUseCase> getJobsUseCaseProvider;

  private final Provider<ChangePasswordUseCase> changePasswordUseCaseProvider;

  public DashboardViewModel_Factory(Provider<GetCurrentUserUseCase> getCurrentUserUseCaseProvider,
      Provider<LogoutUseCase> logoutUseCaseProvider,
      Provider<GetJobsUseCase> getJobsUseCaseProvider,
      Provider<ChangePasswordUseCase> changePasswordUseCaseProvider) {
    this.getCurrentUserUseCaseProvider = getCurrentUserUseCaseProvider;
    this.logoutUseCaseProvider = logoutUseCaseProvider;
    this.getJobsUseCaseProvider = getJobsUseCaseProvider;
    this.changePasswordUseCaseProvider = changePasswordUseCaseProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(getCurrentUserUseCaseProvider.get(), logoutUseCaseProvider.get(), getJobsUseCaseProvider.get(), changePasswordUseCaseProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<GetCurrentUserUseCase> getCurrentUserUseCaseProvider,
      Provider<LogoutUseCase> logoutUseCaseProvider,
      Provider<GetJobsUseCase> getJobsUseCaseProvider,
      Provider<ChangePasswordUseCase> changePasswordUseCaseProvider) {
    return new DashboardViewModel_Factory(getCurrentUserUseCaseProvider, logoutUseCaseProvider, getJobsUseCaseProvider, changePasswordUseCaseProvider);
  }

  public static DashboardViewModel newInstance(GetCurrentUserUseCase getCurrentUserUseCase,
      LogoutUseCase logoutUseCase, GetJobsUseCase getJobsUseCase,
      ChangePasswordUseCase changePasswordUseCase) {
    return new DashboardViewModel(getCurrentUserUseCase, logoutUseCase, getJobsUseCase, changePasswordUseCase);
  }
}
