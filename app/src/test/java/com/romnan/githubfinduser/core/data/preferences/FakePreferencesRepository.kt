package com.romnan.githubfinduser.core.data.preferences

import com.romnan.githubfinduser.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePreferencesRepository : PreferencesRepository {
    private var isDarkModeEnabled: Boolean = false

    override suspend fun saveThemeMode(isDarkMode: Boolean) {
        isDarkModeEnabled = isDarkMode
    }

    override suspend fun isDarkModeEnabled(): Flow<Boolean> = flow {
        emit(isDarkModeEnabled)
    }
}