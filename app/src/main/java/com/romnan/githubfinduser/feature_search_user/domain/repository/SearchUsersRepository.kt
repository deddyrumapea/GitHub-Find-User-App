package com.romnan.githubfinduser.feature_search_user.domain.repository

import com.romnan.githubfinduser.core.util.Resource
import com.romnan.githubfinduser.feature_search_user.domain.model.User
import kotlinx.coroutines.flow.Flow

interface SearchUsersRepository {
    fun searchUsers(query: String): Flow<Resource<List<User>>>
}