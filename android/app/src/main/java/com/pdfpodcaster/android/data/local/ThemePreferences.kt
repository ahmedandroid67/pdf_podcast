package com.pdfpodcaster.android.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class ThemePreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val DARK_MODE = stringPreferencesKey("dark_mode")
        val APP_LANGUAGE = stringPreferencesKey("app_language")
    }

    val darkModeFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[Keys.DARK_MODE]
    }

    suspend fun setDarkMode(mode: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.DARK_MODE] = mode
        }
    }

    val appLanguageFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[Keys.APP_LANGUAGE]
    }

    suspend fun setAppLanguage(lang: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.APP_LANGUAGE] = lang
        }
    }
}