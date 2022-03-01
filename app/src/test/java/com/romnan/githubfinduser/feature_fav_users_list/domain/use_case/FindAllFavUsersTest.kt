package com.romnan.githubfinduser.feature_fav_users_list.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.romnan.githubfinduser.core.domain.model.User
import com.romnan.githubfinduser.core.util.DataFaker
import com.romnan.githubfinduser.feature_fav_users_list.data.repository.FakeFavUsersListRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FindAllFavUsersTest {
    private lateinit var fakeRepository: FakeFavUsersListRepository
    private lateinit var findAllFavUsers: FindAllFavUsers
    private lateinit var favUsersList: List<User>

    @Before
    fun setUp() {
        favUsersList = DataFaker.getUniqueUsers()
        fakeRepository = FakeFavUsersListRepository(favUsersList = favUsersList)
        findAllFavUsers = FindAllFavUsers(fakeRepository)
    }

    @Test
    fun `Get all fav users, fav users list`() = runBlocking {
        val result = findAllFavUsers().first()
        assertThat(result.data).isEqualTo(favUsersList)
    }
}