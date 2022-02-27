package com.romnan.githubfinduser.core.domain.use_case

import com.romnan.githubfinduser.core.domain.repository.PreferencesRepository

class SaveThemeMode(private val repository: PreferencesRepository) {
    suspend operator fun invoke(isDarkTheme: Boolean) {
        repository.saveThemeMode(isDarkTheme)
    }
}