package com.romnan.githubfinduser.core.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romnan.githubfinduser.core.domain.use_case.PreferencesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val useCase: PreferencesUseCases
) : ViewModel() {
    private val _state = MutableLiveData(PreferencesState())
    val state: LiveData<PreferencesState> = _state

    init {
        fetchState()
    }

    private fun fetchState() {
        viewModelScope.launch {
            val isDarkThemePreferred = useCase.isDarkModeEnabled().first()
            _state.value = state.value?.copy(isDarkModeEnabled = isDarkThemePreferred)
        }
    }

    fun onEvent(event: PreferencesEvent) {
        if (event is PreferencesEvent.ToggleDarkTheme) {
            toggleDarkMode()
        }
    }

    private fun toggleDarkMode() {
        viewModelScope.launch {
            state.value?.let { useCase.saveThemeMode(!it.isDarkModeEnabled) }
            fetchState()
        }
    }
}