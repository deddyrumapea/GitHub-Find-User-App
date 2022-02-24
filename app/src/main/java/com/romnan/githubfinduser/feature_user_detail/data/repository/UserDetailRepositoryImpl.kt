package com.romnan.githubfinduser.feature_user_detail.data.repository

import com.romnan.githubfinduser.core.util.Resource
import com.romnan.githubfinduser.feature_user_detail.data.remote.UserDetailApi
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowerUser
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowingUser
import com.romnan.githubfinduser.feature_user_detail.domain.model.UserDetail
import com.romnan.githubfinduser.feature_user_detail.domain.repository.UserDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class UserDetailRepositoryImpl(
    private val api: UserDetailApi
) : UserDetailRepository {
    override fun getUserDetail(username: String): Flow<Resource<UserDetail>> = flow {
        emit(Resource.Loading())
        try {
            val userDetail = api.getUserDetail(username).toUserDetail()
            emit(Resource.Success(userDetail))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_EXCEPTION_MESSAGE))
        } catch (e: Exception) {
            emit(Resource.Error(message = EXCEPTION_MESSAGE))
        }
    }

    override fun getUserFollowersList(username: String): Flow<Resource<List<FollowerUser>>> = flow {
        emit(Resource.Loading(emptyList()))
        try {
            val userFollowersList = api.getUserFollowersList(username).map { it.toFollowerUser() }
            emit(Resource.Success(userFollowersList))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_EXCEPTION_MESSAGE))
        } catch (e: Exception) {
            emit(Resource.Error(message = EXCEPTION_MESSAGE))
        }
    }

    override fun getUserFollowingList(username: String): Flow<Resource<List<FollowingUser>>> =
        flow {
            emit(Resource.Loading(emptyList()))
            try {
                val userFollowingList = api.getUserFollowingList(username)
                    .map { it.toFollowingUser() }
                emit(Resource.Success(userFollowingList))
            } catch (e: IOException) {
                emit(Resource.Error(message = IO_EXCEPTION_MESSAGE))
            } catch (e: Exception) {
                emit(Resource.Error(message = EXCEPTION_MESSAGE))
            }
        }

    companion object {
        private const val IO_EXCEPTION_MESSAGE = "Please check your internet connection"
        private const val EXCEPTION_MESSAGE = "Something went wrong"
    }
}