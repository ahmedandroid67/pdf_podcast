package com.pdfpodcaster.android.data.remote.interceptor;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class ErrorInterceptor_Factory implements Factory<ErrorInterceptor> {
  @Override
  public ErrorInterceptor get() {
    return newInstance();
  }

  public static ErrorInterceptor_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ErrorInterceptor newInstance() {
    return new ErrorInterceptor();
  }

  private static final class InstanceHolder {
    private static final ErrorInterceptor_Factory INSTANCE = new ErrorInterceptor_Factory();
  }
}
