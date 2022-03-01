package com.romnan.githubfinduser.core.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.romnan.githubfinduser.core.data.preferences.FakePreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveThemeModeTest {
    private lateinit var fakeRepository: FakePreferencesRepository
    private lateinit var saveThemeMode: SaveThemeMode

    @Before
    fun setUp() {
        fakeRepository = FakePreferencesRepository()
        saveThemeMode = SaveThemeMode(fakeRepository)
    }

    @Test
    fun `Save dark mode as true, dark mode is enabled`() = runBlocking {
        saveThemeMode(true)
        val result = fakeRepository.isDarkModeEnabled().first()
        assertThat(result).isTrue()
    }

    @Test
    fun `Save dark mode as false, dark mode is disabled`() = runBlocking {
        saveThemeMode(false)
        val result = fakeRepository.isDarkModeEnabled().first()
        assertThat(result).isFalse()
    }
}