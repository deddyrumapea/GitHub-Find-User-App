package com.romnan.githubfinduser.feature_user_detail.domain.use_case

import com.romnan.githubfinduser.core.util.Resource
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowingUser
import com.romnan.githubfinduser.feature_user_detail.domain.repository.UserDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserFollowingList(private val repository: UserDetailRepository) {
    operator fun invoke(username: String): Flow<Resource<List<FollowingUser>>> {
        if (username.isBlank()) return flow { }
        return repository.getUserFollowingList(username)
    }
}