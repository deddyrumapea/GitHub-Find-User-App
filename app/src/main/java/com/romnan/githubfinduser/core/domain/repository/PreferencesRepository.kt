package com.romnan.githubfinduser.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun saveThemeMode(isDarkMode: Boolean)
    suspend fun isDarkModeEnabled(): Flow<Boolean>
}