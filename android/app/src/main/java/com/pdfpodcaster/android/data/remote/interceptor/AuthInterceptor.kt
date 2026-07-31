package com.pdfpodcaster.android.data.remote.interceptor

import com.pdfpodcaster.android.data.local.TokenDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val pathsWithoutAuth = listOf("/auth/login", "/auth/register", "/auth/callback")
        val path = originalRequest.url.encodedPath
        if (pathsWithoutAuth.any { path.contains(it) }) {
            return chain.proceed(originalRequest)
        }

        val token = try {
            runBlocking { tokenDataStore.getToken() }
        } catch (e: Exception) {
            null
        }
        if (token.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        }

        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(authenticatedRequest)
    }
}