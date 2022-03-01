package com.romnan.githubfinduser.feature_search_user.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.romnan.githubfinduser.core.domain.model.User
import com.romnan.githubfinduser.core.util.DataFaker
import com.romnan.githubfinduser.feature_search_user.data.repository.FakeSearchUsersRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchUsersTest {
    private lateinit var fakeRepository: FakeSearchUsersRepository
    private lateinit var searchUsers: SearchUsers
    private lateinit var queriesResults: Map<String, List<User>>

    @Before
    fun setUp() {
        queriesResults = DataFaker.getQueriesResults()
        fakeRepository = FakeSearchUsersRepository(queriesResults = queriesResults)
        searchUsers = SearchUsers(fakeRepository)
    }

    @Test
    fun `Search users by query, resulting list of users`() = runBlocking {
        for (query in queriesResults.keys) {
            val results = searchUsers(query).first()
            val origin = queriesResults[query]
            assertThat(results.data).isEqualTo(origin)
        }
    }

    @Test
    fun `Search users by blank query, no result`() = runBlocking {
        val result = searchUsers("").firstOrNull()
        assertThat(result).isNull()
    }
}