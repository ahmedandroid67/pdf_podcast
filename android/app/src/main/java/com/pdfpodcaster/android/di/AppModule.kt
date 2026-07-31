package com.pdfpodcaster.android.di

import com.pdfpodcaster.android.BuildConfig
import com.pdfpodcaster.android.data.local.TokenDataStore
import com.pdfpodcaster.android.data.remote.api.*
import com.pdfpodcaster.android.data.remote.interceptor.AuthInterceptor
import com.pdfpodcaster.android.data.remote.interceptor.ErrorInterceptor
import com.pdfpodcaster.android.data.repository.*
import com.pdfpodcaster.android.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = BuildConfig.API_BASE_URL

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        errorInterceptor: ErrorInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(errorInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("$BASE_URL/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun providePipelineApi(retrofit: Retrofit): PipelineApi = retrofit.create(PipelineApi::class.java)

    @Provides
    @Singleton
    fun provideJobApi(retrofit: Retrofit): JobApi = retrofit.create(JobApi::class.java)

    @Provides
    @Singleton
    fun provideAdminApi(retrofit: Retrofit): AdminApi = retrofit.create(AdminApi::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    @Singleton
    fun provideJobRepository(impl: JobRepositoryImpl): JobRepository = impl

    @Provides
    @Singleton
    fun provideAdminRepository(impl: AdminRepositoryImpl): AdminRepository = impl
}