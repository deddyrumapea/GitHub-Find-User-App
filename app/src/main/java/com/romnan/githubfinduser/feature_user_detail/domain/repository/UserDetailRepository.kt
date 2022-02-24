package com.romnan.githubfinduser.feature_user_detail.domain.repository

import com.romnan.githubfinduser.core.util.Resource
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowerUser
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowingUser
import com.romnan.githubfinduser.feature_user_detail.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserDetailRepository {
    fun getUserDetail(username: String): Flow<Resource<UserDetail>>
    fun getUserFollowersList(username: String): Flow<Resource<List<FollowerUser>>>
    fun getUserFollowingList(username: String): Flow<Resource<List<FollowingUser>>>
}