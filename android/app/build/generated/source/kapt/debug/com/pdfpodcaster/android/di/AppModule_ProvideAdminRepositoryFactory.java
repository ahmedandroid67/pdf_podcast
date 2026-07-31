package com.pdfpodcaster.android.di;

import com.pdfpodcaster.android.data.repository.AdminRepositoryImpl;
import com.pdfpodcaster.android.domain.repository.AdminRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideAdminRepositoryFactory implements Factory<AdminRepository> {
  private final Provider<AdminRepositoryImpl> implProvider;

  public AppModule_ProvideAdminRepositoryFactory(Provider<AdminRepositoryImpl> implProvider) {
    this.implProvider = implProvider;
  }

  @Override
  public AdminRepository get() {
    return provideAdminRepository(implProvider.get());
  }

  public static AppModule_ProvideAdminRepositoryFactory create(
      Provider<AdminRepositoryImpl> implProvider) {
    return new AppModule_ProvideAdminRepositoryFactory(implProvider);
  }

  public static AdminRepository provideAdminRepository(AdminRepositoryImpl impl) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAdminRepository(impl));
  }
}
