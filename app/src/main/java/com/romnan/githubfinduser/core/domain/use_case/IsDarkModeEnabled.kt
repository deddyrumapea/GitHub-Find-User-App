package com.romnan.githubfinduser.core.domain.use_case

import com.romnan.githubfinduser.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class IsDarkModeEnabled(private val repository: PreferencesRepository) {
    suspend operator fun invoke(): Flow<Boolean> {
        return repository.isDarkModeEnabled()
    }
}