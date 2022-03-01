package com.romnan.githubfinduser.core.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romnan.githubfinduser.core.domain.use_case.PreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val useCase: PreferencesUseCase
) : ViewModel() {
    private val _state = MutableLiveData(PreferencesState())
    val state: LiveData<PreferencesState> = _state

    private var fetchStateJob: Job? = null
    private var toggleDarkModeJob: Job? = null

    init {
        fetchState()
    }

    fun onEvent(event: PreferencesEvent) {
        if (event is PreferencesEvent.ToggleDarkTheme) {
            toggleDarkMode()
        }
    }

    private fun fetchState() {
        fetchStateJob?.cancel()
        fetchStateJob = viewModelScope.launch {
            val isDarkThemePreferred = useCase.isDarkModeEnabled().first()
            _state.value = state.value?.copy(isDarkModeEnabled = isDarkThemePreferred)
        }
    }

    private fun toggleDarkMode() {
        toggleDarkModeJob?.cancel()
        toggleDarkModeJob = viewModelScope.launch {
            state.value?.let { useCase.saveThemeMode(!it.isDarkModeEnabled) }
            fetchState()
        }
    }
}