package com.romnan.githubfinduser.feature_user_detail.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.romnan.githubfinduser.core.util.DataFaker
import com.romnan.githubfinduser.feature_user_detail.data.repository.FakeUserDetailRepository
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowerUser
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUserFollowersListTest {

    private lateinit var getUserFollowersList: GetUserFollowersList
    private lateinit var fakeRepository: FakeUserDetailRepository
    private lateinit var fakeUsernameFollowers: Map<String, List<FollowerUser>>

    @Before
    fun setUp() {
        fakeUsernameFollowers = DataFaker.getUsernameFollowers()
        fakeRepository = FakeUserDetailRepository(usernameFollowers = fakeUsernameFollowers)
        getUserFollowersList = GetUserFollowersList(fakeRepository)
    }

    @Test
    fun `Get user followers list by username, correct followers list`() = runBlocking {
        for (username in fakeUsernameFollowers.keys) {
            val result = getUserFollowersList(username).first()
            val origin = fakeUsernameFollowers[username]
            assertThat(result.data).isEqualTo(origin)
        }
    }

    @Test
    fun `Get user followers list by blank username, no result`() = runBlocking {
        val result = getUserFollowersList("").firstOrNull()
        assertThat(result).isNull()
    }
}