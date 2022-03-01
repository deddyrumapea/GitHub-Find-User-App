package com.romnan.githubfinduser.feature_user_detail.domain.use_case

import com.romnan.githubfinduser.core.domain.util.Resource
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowerUser
import com.romnan.githubfinduser.feature_user_detail.domain.repository.UserDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserFollowersList(private val repository: UserDetailRepository) {
    operator fun invoke(username: String): Flow<Resource<List<FollowerUser>>> {
        if (username.isBlank()) return flow { }
        return repository.getUserFollowersList(username)
    }
}