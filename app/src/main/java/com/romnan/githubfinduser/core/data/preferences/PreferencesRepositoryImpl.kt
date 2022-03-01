package com.romnan.githubfinduser.core.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.romnan.githubfinduser.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesRepositoryImpl(
    appContext: Context
) : PreferencesRepository {
    private val Context.dataStore by preferencesDataStore(PREF_NAME)

    private val dataStore = appContext.dataStore
    private val keyDarkTheme = booleanPreferencesKey(DARK_THEME_PREF_NAME)

    override suspend fun saveThemeMode(isDarkMode: Boolean) {
        dataStore.edit { pref ->
            pref[keyDarkTheme] = isDarkMode
        }
    }

    override suspend fun isDarkModeEnabled(): Flow<Boolean> {
        return dataStore.data.map { pref ->
            pref[keyDarkTheme] ?: false
        }
    }

    companion object {
        private const val DARK_THEME_PREF_NAME = "dark_mode"
        const val PREF_NAME = "pref_gfu"
    }
}