package com.romnan.githubfinduser.core.domain.use_case

data class PreferencesUseCase(
    val saveThemeMode: SaveThemeMode,
    val isDarkModeEnabled: IsDarkModeEnabled
)