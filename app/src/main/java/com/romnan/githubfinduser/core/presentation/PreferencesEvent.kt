package com.romnan.githubfinduser.core.presentation

sealed class PreferencesEvent {
    object ToggleDarkTheme : PreferencesEvent()
}
