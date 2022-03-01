package com.romnan.githubfinduser.feature_search_user.domain.use_case

import com.romnan.githubfinduser.core.domain.util.Resource
import com.romnan.githubfinduser.core.domain.model.User
import com.romnan.githubfinduser.feature_search_user.domain.repository.SearchUsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchUsers(private val repository: SearchUsersRepository) {
    operator fun invoke(query: String): Flow<Resource<List<User>>> {
        if (query.isBlank()) return flow { }
        return repository.searchUsers(query)
    }
}