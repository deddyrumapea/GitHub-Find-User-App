package com.romnan.githubfinduser.core.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.romnan.githubfinduser.core.data.preferences.FakePreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class IsDarkModeEnabledTest {
    private lateinit var fakeRepository: FakePreferencesRepository
    private lateinit var isDarkModeEnabled: IsDarkModeEnabled

    @Before
    fun setUp() {
        fakeRepository = FakePreferencesRepository()
        isDarkModeEnabled = IsDarkModeEnabled(fakeRepository)
    }

    @Test
    fun `Check enabled dark mode, true`() = runBlocking {
        fakeRepository.saveThemeMode(true)
        val result = isDarkModeEnabled().first()
        assertThat(result).isTrue()
    }

    @Test
    fun `Check disabled dark mode, false`() = runBlocking {
        fakeRepository.saveThemeMode(false)
        val result = isDarkModeEnabled().first()
        assertThat(result).isFalse()
    }
}