package com.pdfpodcaster.android.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "auth_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_PLAN = "user_plan"
    }

    suspend fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    suspend fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    suspend fun clearToken() {
        prefs.edit().remove(KEY_TOKEN).apply()
    }

    suspend fun saveUserEmail(email: String) {
        prefs.edit().putString(KEY_USER_EMAIL, email).apply()
    }

    suspend fun getUserEmail(): String? {
        return prefs.getString(KEY_USER_EMAIL, null)
    }

    suspend fun saveUserPlan(plan: String) {
        prefs.edit().putString(KEY_USER_PLAN, plan).apply()
    }

    suspend fun getUserPlan(): String? {
        return prefs.getString(KEY_USER_PLAN, null)
    }

    suspend fun clearAll() {
        prefs.edit().clear().apply()
    }
}