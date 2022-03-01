package com.romnan.githubfinduser.feature_user_detail.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.romnan.githubfinduser.core.util.DataFaker
import com.romnan.githubfinduser.feature_user_detail.data.repository.FakeUserDetailRepository
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowingUser
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUserFollowingListTest {

    private lateinit var getUserFollowingList: GetUserFollowingList
    private lateinit var fakeRepository: FakeUserDetailRepository
    private lateinit var fakeUsernameFollowings: Map<String, List<FollowingUser>>

    @Before
    fun setUp() {
        fakeUsernameFollowings = DataFaker.getUsernameFollowing()
        fakeRepository = FakeUserDetailRepository(usernameFollowing = fakeUsernameFollowings)
        getUserFollowingList = GetUserFollowingList(fakeRepository)
    }

    @Test
    fun `Get user following list by username, correct following list`() = runBlocking {
        for (username in fakeUsernameFollowings.keys) {
            val result = getUserFollowingList(username).first()
            val origin = fakeUsernameFollowings[username]
            assertThat(result.data).isEqualTo(origin)
        }
    }

    @Test
    fun `Get user following list by blank username, no result`() = runBlocking {
        val result = getUserFollowingList("").firstOrNull()
        assertThat(result).isNull()
    }
}