package com.romnan.githubfinduser.feature_user_detail.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.romnan.githubfinduser.core.util.DataFaker
import com.romnan.githubfinduser.feature_user_detail.data.repository.FakeUserDetailRepository
import com.romnan.githubfinduser.feature_user_detail.domain.model.UserDetail
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUserDetailTest {
    private lateinit var getUserDetail: GetUserDetail
    private lateinit var fakeRepository: FakeUserDetailRepository
    private lateinit var fakeUserDetails: List<UserDetail>

    @Before
    fun setUp() {
        fakeUserDetails = DataFaker.getUserDetailsList()
        fakeRepository = FakeUserDetailRepository(userDetailsList = fakeUserDetails)
        getUserDetail = GetUserDetail(fakeRepository)
    }

    @Test
    fun `Get UserDetail by username, correct UserDetail`() = runBlocking {
        for (userDetail in fakeUserDetails) {
            val result = getUserDetail(userDetail.username).first()
            assertThat(result.data).isEqualTo(userDetail)
        }
    }

    @Test
    fun `Get UserDetail by blank username, no result`() = runBlocking {
        val result = getUserDetail("").firstOrNull()
        assertThat(result).isNull()
    }
}