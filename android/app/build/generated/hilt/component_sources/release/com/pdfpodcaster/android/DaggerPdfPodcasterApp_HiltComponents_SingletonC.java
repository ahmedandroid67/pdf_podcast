package com.pdfpodcaster.android;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.pdfpodcaster.android.data.local.TokenDataStore;
import com.pdfpodcaster.android.data.remote.api.AdminApi;
import com.pdfpodcaster.android.data.remote.api.AuthApi;
import com.pdfpodcaster.android.data.remote.api.JobApi;
import com.pdfpodcaster.android.data.remote.interceptor.AuthInterceptor;
import com.pdfpodcaster.android.data.remote.interceptor.ErrorInterceptor;
import com.pdfpodcaster.android.data.repository.AdminRepositoryImpl;
import com.pdfpodcaster.android.data.repository.AuthRepositoryImpl;
import com.pdfpodcaster.android.data.repository.JobRepositoryImpl;
import com.pdfpodcaster.android.di.AppModule_ProvideAdminApiFactory;
import com.pdfpodcaster.android.di.AppModule_ProvideAdminRepositoryFactory;
import com.pdfpodcaster.android.di.AppModule_ProvideAuthApiFactory;
import com.pdfpodcaster.android.di.AppModule_ProvideAuthRepositoryFactory;
import com.pdfpodcaster.android.di.AppModule_ProvideJobApiFactory;
import com.pdfpodcaster.android.di.AppModule_ProvideJobRepositoryFactory;
import com.pdfpodcaster.android.di.AppModule_ProvideJsonFactory;
import com.pdfpodcaster.android.di.AppModule_ProvideOkHttpClientFactory;
import com.pdfpodcaster.android.di.AppModule_ProvideRetrofitFactory;
import com.pdfpodcaster.android.domain.repository.AdminRepository;
import com.pdfpodcaster.android.domain.repository.AuthRepository;
import com.pdfpodcaster.android.domain.repository.JobRepository;
import com.pdfpodcaster.android.domain.usecase.BulkDeleteOldUsersUseCase;
import com.pdfpodcaster.android.domain.usecase.ChangePasswordUseCase;
import com.pdfpodcaster.android.domain.usecase.CheckAuthUseCase;
import com.pdfpodcaster.android.domain.usecase.CreateAdminUserUseCase;
import com.pdfpodcaster.android.domain.usecase.DeleteAdminUserUseCase;
import com.pdfpodcaster.android.domain.usecase.GetAdminStatsUseCase;
import com.pdfpodcaster.android.domain.usecase.GetAdminUsersUseCase;
import com.pdfpodcaster.android.domain.usecase.GetCurrentUserUseCase;
import com.pdfpodcaster.android.domain.usecase.GetJobResultUseCase;
import com.pdfpodcaster.android.domain.usecase.GetJobsUseCase;
import com.pdfpodcaster.android.domain.usecase.LoginUseCase;
import com.pdfpodcaster.android.domain.usecase.LogoutUseCase;
import com.pdfpodcaster.android.domain.usecase.PollJobProgressUseCase;
import com.pdfpodcaster.android.domain.usecase.ProcessUploadUseCase;
import com.pdfpodcaster.android.domain.usecase.ProcessUrlUseCase;
import com.pdfpodcaster.android.domain.usecase.RegisterUseCase;
import com.pdfpodcaster.android.domain.usecase.ResetAdminPasswordUseCase;
import com.pdfpodcaster.android.domain.usecase.ToggleAdminUserStatusUseCase;
import com.pdfpodcaster.android.domain.usecase.UpdateAdminUserUseCase;
import com.pdfpodcaster.android.ui.screens.admin.AdminDashboardViewModel;
import com.pdfpodcaster.android.ui.screens.admin.AdminDashboardViewModel_HiltModules_KeyModule_ProvideFactory;
import com.pdfpodcaster.android.ui.screens.audioplayer.AudioPlayerViewModel;
import com.pdfpodcaster.android.ui.screens.audioplayer.AudioPlayerViewModel_HiltModules_KeyModule_ProvideFactory;
import com.pdfpodcaster.android.ui.screens.dashboard.DashboardViewModel;
import com.pdfpodcaster.android.ui.screens.dashboard.DashboardViewModel_HiltModules_KeyModule_ProvideFactory;
import com.pdfpodcaster.android.ui.screens.jobhistory.JobHistoryViewModel;
import com.pdfpodcaster.android.ui.screens.jobhistory.JobHistoryViewModel_HiltModules_KeyModule_ProvideFactory;
import com.pdfpodcaster.android.ui.screens.login.LoginViewModel;
import com.pdfpodcaster.android.ui.screens.login.LoginViewModel_HiltModules_KeyModule_ProvideFactory;
import com.pdfpodcaster.android.ui.screens.oauthcallback.OAuthCallbackViewModel;
import com.pdfpodcaster.android.ui.screens.oauthcallback.OAuthCallbackViewModel_HiltModules_KeyModule_ProvideFactory;
import com.pdfpodcaster.android.ui.screens.pipeline.PodcastPipelineViewModel;
import com.pdfpodcaster.android.ui.screens.pipeline.PodcastPipelineViewModel_HiltModules_KeyModule_ProvideFactory;
import com.pdfpodcaster.android.ui.screens.register.RegisterViewModel;
import com.pdfpodcaster.android.ui.screens.register.RegisterViewModel_HiltModules_KeyModule_ProvideFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import kotlinx.serialization.json.Json;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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
public final class DaggerPdfPodcasterApp_HiltComponents_SingletonC {
  private DaggerPdfPodcasterApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public PdfPodcasterApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements PdfPodcasterApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public PdfPodcasterApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements PdfPodcasterApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public PdfPodcasterApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements PdfPodcasterApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public PdfPodcasterApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements PdfPodcasterApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public PdfPodcasterApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements PdfPodcasterApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public PdfPodcasterApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements PdfPodcasterApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public PdfPodcasterApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements PdfPodcasterApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public PdfPodcasterApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends PdfPodcasterApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends PdfPodcasterApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends PdfPodcasterApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends PdfPodcasterApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return ImmutableSet.<String>of(AdminDashboardViewModel_HiltModules_KeyModule_ProvideFactory.provide(), AudioPlayerViewModel_HiltModules_KeyModule_ProvideFactory.provide(), DashboardViewModel_HiltModules_KeyModule_ProvideFactory.provide(), JobHistoryViewModel_HiltModules_KeyModule_ProvideFactory.provide(), LoginViewModel_HiltModules_KeyModule_ProvideFactory.provide(), OAuthCallbackViewModel_HiltModules_KeyModule_ProvideFactory.provide(), PodcastPipelineViewModel_HiltModules_KeyModule_ProvideFactory.provide(), RegisterViewModel_HiltModules_KeyModule_ProvideFactory.provide());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends PdfPodcasterApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AdminDashboardViewModel> adminDashboardViewModelProvider;

    private Provider<AudioPlayerViewModel> audioPlayerViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<JobHistoryViewModel> jobHistoryViewModelProvider;

    private Provider<LoginViewModel> loginViewModelProvider;

    private Provider<OAuthCallbackViewModel> oAuthCallbackViewModelProvider;

    private Provider<PodcastPipelineViewModel> podcastPipelineViewModelProvider;

    private Provider<RegisterViewModel> registerViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private GetAdminStatsUseCase getAdminStatsUseCase() {
      return new GetAdminStatsUseCase(singletonCImpl.provideAdminRepositoryProvider.get());
    }

    private GetAdminUsersUseCase getAdminUsersUseCase() {
      return new GetAdminUsersUseCase(singletonCImpl.provideAdminRepositoryProvider.get());
    }

    private CreateAdminUserUseCase createAdminUserUseCase() {
      return new CreateAdminUserUseCase(singletonCImpl.provideAdminRepositoryProvider.get());
    }

    private UpdateAdminUserUseCase updateAdminUserUseCase() {
      return new UpdateAdminUserUseCase(singletonCImpl.provideAdminRepositoryProvider.get());
    }

    private ToggleAdminUserStatusUseCase toggleAdminUserStatusUseCase() {
      return new ToggleAdminUserStatusUseCase(singletonCImpl.provideAdminRepositoryProvider.get());
    }

    private ResetAdminPasswordUseCase resetAdminPasswordUseCase() {
      return new ResetAdminPasswordUseCase(singletonCImpl.provideAdminRepositoryProvider.get());
    }

    private DeleteAdminUserUseCase deleteAdminUserUseCase() {
      return new DeleteAdminUserUseCase(singletonCImpl.provideAdminRepositoryProvider.get());
    }

    private BulkDeleteOldUsersUseCase bulkDeleteOldUsersUseCase() {
      return new BulkDeleteOldUsersUseCase(singletonCImpl.provideAdminRepositoryProvider.get());
    }

    private GetJobResultUseCase getJobResultUseCase() {
      return new GetJobResultUseCase(singletonCImpl.provideJobRepositoryProvider.get());
    }

    private GetCurrentUserUseCase getCurrentUserUseCase() {
      return new GetCurrentUserUseCase(singletonCImpl.provideAuthRepositoryProvider.get());
    }

    private LogoutUseCase logoutUseCase() {
      return new LogoutUseCase(singletonCImpl.provideAuthRepositoryProvider.get());
    }

    private GetJobsUseCase getJobsUseCase() {
      return new GetJobsUseCase(singletonCImpl.provideJobRepositoryProvider.get());
    }

    private ChangePasswordUseCase changePasswordUseCase() {
      return new ChangePasswordUseCase(singletonCImpl.provideAuthRepositoryProvider.get());
    }

    private LoginUseCase loginUseCase() {
      return new LoginUseCase(singletonCImpl.provideAuthRepositoryProvider.get());
    }

    private CheckAuthUseCase checkAuthUseCase() {
      return new CheckAuthUseCase(singletonCImpl.provideAuthRepositoryProvider.get());
    }

    private ProcessUploadUseCase processUploadUseCase() {
      return new ProcessUploadUseCase(singletonCImpl.provideJobRepositoryProvider.get());
    }

    private ProcessUrlUseCase processUrlUseCase() {
      return new ProcessUrlUseCase(singletonCImpl.provideJobRepositoryProvider.get());
    }

    private PollJobProgressUseCase pollJobProgressUseCase() {
      return new PollJobProgressUseCase(singletonCImpl.provideJobRepositoryProvider.get());
    }

    private RegisterUseCase registerUseCase() {
      return new RegisterUseCase(singletonCImpl.provideAuthRepositoryProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.adminDashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.audioPlayerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.jobHistoryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.loginViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.oAuthCallbackViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.podcastPipelineViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.registerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
    }

    @Override
    public Map<String, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return ImmutableMap.<String, javax.inject.Provider<ViewModel>>builderWithExpectedSize(8).put("com.pdfpodcaster.android.ui.screens.admin.AdminDashboardViewModel", ((Provider) adminDashboardViewModelProvider)).put("com.pdfpodcaster.android.ui.screens.audioplayer.AudioPlayerViewModel", ((Provider) audioPlayerViewModelProvider)).put("com.pdfpodcaster.android.ui.screens.dashboard.DashboardViewModel", ((Provider) dashboardViewModelProvider)).put("com.pdfpodcaster.android.ui.screens.jobhistory.JobHistoryViewModel", ((Provider) jobHistoryViewModelProvider)).put("com.pdfpodcaster.android.ui.screens.login.LoginViewModel", ((Provider) loginViewModelProvider)).put("com.pdfpodcaster.android.ui.screens.oauthcallback.OAuthCallbackViewModel", ((Provider) oAuthCallbackViewModelProvider)).put("com.pdfpodcaster.android.ui.screens.pipeline.PodcastPipelineViewModel", ((Provider) podcastPipelineViewModelProvider)).put("com.pdfpodcaster.android.ui.screens.register.RegisterViewModel", ((Provider) registerViewModelProvider)).build();
    }

    @Override
    public Map<String, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<String, Object>of();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.pdfpodcaster.android.ui.screens.admin.AdminDashboardViewModel 
          return (T) new AdminDashboardViewModel(viewModelCImpl.getAdminStatsUseCase(), viewModelCImpl.getAdminUsersUseCase(), viewModelCImpl.createAdminUserUseCase(), viewModelCImpl.updateAdminUserUseCase(), viewModelCImpl.toggleAdminUserStatusUseCase(), viewModelCImpl.resetAdminPasswordUseCase(), viewModelCImpl.deleteAdminUserUseCase(), viewModelCImpl.bulkDeleteOldUsersUseCase());

          case 1: // com.pdfpodcaster.android.ui.screens.audioplayer.AudioPlayerViewModel 
          return (T) new AudioPlayerViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), viewModelCImpl.getJobResultUseCase(), singletonCImpl.tokenDataStoreProvider.get());

          case 2: // com.pdfpodcaster.android.ui.screens.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(viewModelCImpl.getCurrentUserUseCase(), viewModelCImpl.logoutUseCase(), viewModelCImpl.getJobsUseCase(), viewModelCImpl.changePasswordUseCase());

          case 3: // com.pdfpodcaster.android.ui.screens.jobhistory.JobHistoryViewModel 
          return (T) new JobHistoryViewModel(viewModelCImpl.getJobsUseCase());

          case 4: // com.pdfpodcaster.android.ui.screens.login.LoginViewModel 
          return (T) new LoginViewModel(viewModelCImpl.loginUseCase(), viewModelCImpl.checkAuthUseCase());

          case 5: // com.pdfpodcaster.android.ui.screens.oauthcallback.OAuthCallbackViewModel 
          return (T) new OAuthCallbackViewModel(singletonCImpl.tokenDataStoreProvider.get(), viewModelCImpl.loginUseCase());

          case 6: // com.pdfpodcaster.android.ui.screens.pipeline.PodcastPipelineViewModel 
          return (T) new PodcastPipelineViewModel(viewModelCImpl.processUploadUseCase(), viewModelCImpl.processUrlUseCase(), viewModelCImpl.pollJobProgressUseCase(), viewModelCImpl.getJobResultUseCase());

          case 7: // com.pdfpodcaster.android.ui.screens.register.RegisterViewModel 
          return (T) new RegisterViewModel(viewModelCImpl.registerUseCase());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends PdfPodcasterApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends PdfPodcasterApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends PdfPodcasterApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<TokenDataStore> tokenDataStoreProvider;

    private Provider<AuthInterceptor> authInterceptorProvider;

    private Provider<ErrorInterceptor> errorInterceptorProvider;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<Json> provideJsonProvider;

    private Provider<Retrofit> provideRetrofitProvider;

    private Provider<AdminApi> provideAdminApiProvider;

    private Provider<AdminRepositoryImpl> adminRepositoryImplProvider;

    private Provider<AdminRepository> provideAdminRepositoryProvider;

    private Provider<JobApi> provideJobApiProvider;

    private Provider<JobRepositoryImpl> jobRepositoryImplProvider;

    private Provider<JobRepository> provideJobRepositoryProvider;

    private Provider<AuthApi> provideAuthApiProvider;

    private Provider<AuthRepositoryImpl> authRepositoryImplProvider;

    private Provider<AuthRepository> provideAuthRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.tokenDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<TokenDataStore>(singletonCImpl, 6));
      this.authInterceptorProvider = DoubleCheck.provider(new SwitchingProvider<AuthInterceptor>(singletonCImpl, 5));
      this.errorInterceptorProvider = DoubleCheck.provider(new SwitchingProvider<ErrorInterceptor>(singletonCImpl, 7));
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 4));
      this.provideJsonProvider = DoubleCheck.provider(new SwitchingProvider<Json>(singletonCImpl, 8));
      this.provideRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 3));
      this.provideAdminApiProvider = DoubleCheck.provider(new SwitchingProvider<AdminApi>(singletonCImpl, 2));
      this.adminRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<AdminRepositoryImpl>(singletonCImpl, 1));
      this.provideAdminRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AdminRepository>(singletonCImpl, 0));
      this.provideJobApiProvider = DoubleCheck.provider(new SwitchingProvider<JobApi>(singletonCImpl, 11));
      this.jobRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<JobRepositoryImpl>(singletonCImpl, 10));
      this.provideJobRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<JobRepository>(singletonCImpl, 9));
      this.provideAuthApiProvider = DoubleCheck.provider(new SwitchingProvider<AuthApi>(singletonCImpl, 14));
      this.authRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepositoryImpl>(singletonCImpl, 13));
      this.provideAuthRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepository>(singletonCImpl, 12));
    }

    @Override
    public void injectPdfPodcasterApp(PdfPodcasterApp pdfPodcasterApp) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.pdfpodcaster.android.domain.repository.AdminRepository 
          return (T) AppModule_ProvideAdminRepositoryFactory.provideAdminRepository(singletonCImpl.adminRepositoryImplProvider.get());

          case 1: // com.pdfpodcaster.android.data.repository.AdminRepositoryImpl 
          return (T) new AdminRepositoryImpl(singletonCImpl.provideAdminApiProvider.get());

          case 2: // com.pdfpodcaster.android.data.remote.api.AdminApi 
          return (T) AppModule_ProvideAdminApiFactory.provideAdminApi(singletonCImpl.provideRetrofitProvider.get());

          case 3: // retrofit2.Retrofit 
          return (T) AppModule_ProvideRetrofitFactory.provideRetrofit(singletonCImpl.provideOkHttpClientProvider.get(), singletonCImpl.provideJsonProvider.get());

          case 4: // okhttp3.OkHttpClient 
          return (T) AppModule_ProvideOkHttpClientFactory.provideOkHttpClient(singletonCImpl.authInterceptorProvider.get(), singletonCImpl.errorInterceptorProvider.get());

          case 5: // com.pdfpodcaster.android.data.remote.interceptor.AuthInterceptor 
          return (T) new AuthInterceptor(singletonCImpl.tokenDataStoreProvider.get());

          case 6: // com.pdfpodcaster.android.data.local.TokenDataStore 
          return (T) new TokenDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 7: // com.pdfpodcaster.android.data.remote.interceptor.ErrorInterceptor 
          return (T) new ErrorInterceptor();

          case 8: // kotlinx.serialization.json.Json 
          return (T) AppModule_ProvideJsonFactory.provideJson();

          case 9: // com.pdfpodcaster.android.domain.repository.JobRepository 
          return (T) AppModule_ProvideJobRepositoryFactory.provideJobRepository(singletonCImpl.jobRepositoryImplProvider.get());

          case 10: // com.pdfpodcaster.android.data.repository.JobRepositoryImpl 
          return (T) new JobRepositoryImpl(singletonCImpl.provideJobApiProvider.get());

          case 11: // com.pdfpodcaster.android.data.remote.api.JobApi 
          return (T) AppModule_ProvideJobApiFactory.provideJobApi(singletonCImpl.provideRetrofitProvider.get());

          case 12: // com.pdfpodcaster.android.domain.repository.AuthRepository 
          return (T) AppModule_ProvideAuthRepositoryFactory.provideAuthRepository(singletonCImpl.authRepositoryImplProvider.get());

          case 13: // com.pdfpodcaster.android.data.repository.AuthRepositoryImpl 
          return (T) new AuthRepositoryImpl(singletonCImpl.provideAuthApiProvider.get(), singletonCImpl.tokenDataStoreProvider.get());

          case 14: // com.pdfpodcaster.android.data.remote.api.AuthApi 
          return (T) AppModule_ProvideAuthApiFactory.provideAuthApi(singletonCImpl.provideRetrofitProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
