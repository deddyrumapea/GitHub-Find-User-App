package com.romnan.githubfinduser.feature_search_user.data.repository

import com.romnan.githubfinduser.core.domain.model.User
import com.romnan.githubfinduser.core.domain.util.Resource
import com.romnan.githubfinduser.feature_search_user.domain.repository.SearchUsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSearchUsersRepository(
    val queriesResults: Map<String, List<User>>
) : SearchUsersRepository {
    override fun searchUsers(query: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Success(queriesResults[query]))
    }
}