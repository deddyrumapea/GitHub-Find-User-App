package com.romnan.githubfinduser.feature_user_detail.data.repository

import com.romnan.githubfinduser.core.domain.model.User
import com.romnan.githubfinduser.core.domain.util.Resource
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowerUser
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowingUser
import com.romnan.githubfinduser.feature_user_detail.domain.model.UserDetail
import com.romnan.githubfinduser.feature_user_detail.domain.repository.UserDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserDetailRepository(
    private val userDetailsList: List<UserDetail> = mutableListOf(),
    private val usernameFollowers: Map<String, List<FollowerUser>> = mutableMapOf(),
    private val usernameFollowing: Map<String, List<FollowingUser>> = mutableMapOf(),
    private val favUsernames: MutableList<String> = mutableListOf(),
) : UserDetailRepository {

    override fun getUserDetail(username: String): Flow<Resource<UserDetail>> = flow {
        val fakeUserDetail = userDetailsList.find { it.username == username }
        emit(Resource.Success(fakeUserDetail))
    }

    override fun getUserFollowersList(username: String): Flow<Resource<List<FollowerUser>>> = flow {
        val fakeFollowersList = usernameFollowers[username]
        emit(Resource.Success(fakeFollowersList))
    }

    override fun getUserFollowingList(username: String): Flow<Resource<List<FollowingUser>>> =
        flow {
            val fakeFollowingList = usernameFollowing[username]
            emit(Resource.Success(fakeFollowingList))
        }

    override suspend fun isFavUser(username: String): Boolean {
        return favUsernames.contains(username)
    }

    override suspend fun insertFavUser(user: User) {
        favUsernames.add(user.username)
    }

    override suspend fun deleteFavUser(user: User) {
        favUsernames.remove(user.username)
    }
}