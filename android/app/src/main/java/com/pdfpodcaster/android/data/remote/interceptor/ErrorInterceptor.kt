package com.pdfpodcaster.android.data.remote.interceptor

import kotlinx.serialization.json.Json
import java.io.IOException
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorInterceptor @Inject constructor() : Interceptor {

    private val json = Json { ignoreUnknownKeys = true }

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (!response.isSuccessful) {
            val errorBody = response.body?.string()
            val message = errorBody?.let {
                try {
                    val jsonElement = json.parseToJsonElement(it)
                    jsonElement.jsonObject["detail"]?.jsonPrimitive?.content ?: it
                } catch (_: Exception) {
                    it
                }
            } ?: "Unknown error"

            throw ApiException(response.code, message)
        }

        return response
    }
}

class ApiException(val statusCode: Int, override val message: String) : IOException(message)