package com.example.musicgame.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

val Context.appSettingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

class AppSettingsDataStore(private val context: Context) {

    private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    private val ARE_SOUND_EFFECTS_ENABLED = booleanPreferencesKey("sound_effects_enabled")
    private val ARE_NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    private val APP_LANGUAGE = stringPreferencesKey("app_language") // Clave para el idioma

    val isDarkMode: Flow<Boolean> = context.appSettingsDataStore.data
        .map { preferences ->
            preferences[IS_DARK_MODE] ?: false
        }

    val areSoundEffectsEnabled: Flow<Boolean> = context.appSettingsDataStore.data
        .map { preferences ->
            preferences[ARE_SOUND_EFFECTS_ENABLED] ?: true
        }

    val areNotificationsEnabled: Flow<Boolean> = context.appSettingsDataStore.data
        .map { preferences ->
            preferences[ARE_NOTIFICATIONS_ENABLED] ?: true
        }

    val appLanguage: Flow<String> = context.appSettingsDataStore.data
        .map { preferences ->
            // Por defecto, "system" para que use el LocaleListCompat.getEmptyLocaleList()
            preferences[APP_LANGUAGE] ?: "system"
        }

    suspend fun saveDarkModePreference(isDark: Boolean) {
        context.appSettingsDataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDark
        }
    }

    suspend fun saveSoundEffectsPreference(enabled: Boolean) {
        context.appSettingsDataStore.edit { preferences ->
            preferences[ARE_SOUND_EFFECTS_ENABLED] = enabled
        }
    }

    suspend fun saveNotificationsPreference(enabled: Boolean) {
        context.appSettingsDataStore.edit { preferences ->
            preferences[ARE_NOTIFICATIONS_ENABLED] = enabled
        }
    }

    suspend fun saveAppLanguage(languageTag: String) {
        context.appSettingsDataStore.edit { preferences ->
            preferences[APP_LANGUAGE] = languageTag
        }
    }
}