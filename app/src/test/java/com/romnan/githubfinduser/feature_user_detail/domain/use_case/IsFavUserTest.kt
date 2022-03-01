package com.romnan.githubfinduser.feature_user_detail.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.romnan.githubfinduser.feature_user_detail.data.repository.FakeUserDetailRepository
import com.romnan.githubfinduser.core.util.DataFaker
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class IsFavUserTest {
    private lateinit var fakeRepository: FakeUserDetailRepository
    private lateinit var isFavUser: IsFavUser
    private lateinit var favUsernames: List<String>
    private lateinit var nonFavUsernames: List<String>

    @Before
    fun setUp() {
        favUsernames = DataFaker.getUniqueUsernames()
        nonFavUsernames = DataFaker.getUniqueUsernames()
        fakeRepository = FakeUserDetailRepository(favUsernames = favUsernames.toMutableList())
        isFavUser = IsFavUser(fakeRepository)
    }

    @Test
    fun `Check favorite user, true`() = runBlocking {
        for (username in favUsernames) {
            val result = isFavUser(username = username)
            assertThat(result).isTrue()
        }
    }

    @Test
    fun `Check not favorite user, false`() = runBlocking {
        for (username in nonFavUsernames) {
            val result = isFavUser(username = username)
            assertThat(result).isFalse()
        }
    }
}