package com.romnan.githubfinduser.feature_user_detail.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.romnan.githubfinduser.core.domain.model.User
import com.romnan.githubfinduser.feature_user_detail.data.repository.FakeUserDetailRepository
import com.romnan.githubfinduser.core.util.DataFaker
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class InsertFavUserTest {
    private lateinit var insertFavUser: InsertFavUser
    private lateinit var fakeRepository: FakeUserDetailRepository
    private lateinit var favUsernames: List<String>
    private lateinit var nonFavUsernames: List<String>

    @Before
    fun setUp() {
        favUsernames = DataFaker.getUniqueUsernames()
        nonFavUsernames = DataFaker.getUniqueUsernames()
        fakeRepository = FakeUserDetailRepository(favUsernames = favUsernames.toMutableList())
        insertFavUser = InsertFavUser(fakeRepository)
    }

    @Test
    fun `Insert non fav user to fav list, user is in fav list`() = runBlocking {
        for (username in nonFavUsernames) {
            val user = User(
                avatarUrl = "https://avatars.githubusercontent.com/u/${Random.nextInt()}?v=4",
                username = username,
                type = if (Random.nextBoolean()) "User" else "Organization"
            )
            insertFavUser(user)
            assertThat(fakeRepository.isFavUser(username))
        }
    }

    @Test
    fun `Insert fav user to fav list, user is in fav list`() = runBlocking {
        for (username in favUsernames) {
            val user = User(
                avatarUrl = "https://avatars.githubusercontent.com/u/${Random.nextInt()}?v=4",
                username = username,
                type = if (Random.nextBoolean()) "User" else "Organization"
            )
            insertFavUser(user)
            assertThat(fakeRepository.isFavUser(username))
        }
    }
}