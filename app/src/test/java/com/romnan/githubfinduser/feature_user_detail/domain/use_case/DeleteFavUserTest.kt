package com.romnan.githubfinduser.feature_user_detail.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.romnan.githubfinduser.core.domain.model.User
import com.romnan.githubfinduser.feature_user_detail.data.repository.FakeUserDetailRepository
import com.romnan.githubfinduser.core.util.DataFaker
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class DeleteFavUserTest {
    private lateinit var deleteFavUser: DeleteFavUser
    private lateinit var fakeRepo: FakeUserDetailRepository
    private lateinit var fakeUsernames: List<String>

    @Before
    fun setUp() {
        fakeUsernames = DataFaker.getUniqueUsernames()
        fakeRepo = FakeUserDetailRepository(favUsernames = fakeUsernames.toMutableList())
        deleteFavUser = DeleteFavUser(fakeRepo)
    }

    @Test
    fun `Delete fav user from fav list, user is not in fav list`() = runBlocking {
        for (username in fakeUsernames) {
            val user = User(
                avatarUrl = "https://avatars.githubusercontent.com/u/${Random.nextInt()}?v=4",
                username = username,
                type = if (Random.nextBoolean()) "User" else "Organization"
            )

            assertThat(fakeRepo.isFavUser(username)).isTrue()
            deleteFavUser(user)
            assertThat(fakeRepo.isFavUser(username)).isFalse()
        }
    }

    @Test
    fun `Delete non fav user from fav list, user is not in fav list`() = runBlocking {
        for (username in fakeUsernames) {
            val nonFavUsername = username.reversed()

            val user = User(
                avatarUrl = "https://avatars.githubusercontent.com/u/${Random.nextInt()}?v=4",
                username = nonFavUsername,
                type = if (Random.nextBoolean()) "User" else "Organization"
            )

            deleteFavUser(user)
            assertThat(fakeRepo.isFavUser(nonFavUsername)).isFalse()
        }
    }
}