package com.romnan.githubfinduser.feature_user_detail.data.repository

import com.romnan.githubfinduser.core.data.local.CoreDao
import com.romnan.githubfinduser.core.data.local.entity.FavUserEntity
import com.romnan.githubfinduser.core.domain.model.User
import com.romnan.githubfinduser.core.domain.util.Resource
import com.romnan.githubfinduser.feature_user_detail.data.remote.UserDetailApi
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowerUser
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowingUser
import com.romnan.githubfinduser.feature_user_detail.domain.model.UserDetail
import com.romnan.githubfinduser.feature_user_detail.domain.repository.UserDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class UserDetailRepositoryImpl(
    private val userDetailApi: UserDetailApi,
    private val coreDao: CoreDao
) : UserDetailRepository {
    override fun getUserDetail(username: String): Flow<Resource<UserDetail>> = flow {
        emit(Resource.Loading())
        try {
            val userDetail = userDetailApi.getUserDetail(username).toUserDetail()
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
            val userFollowersList =
                userDetailApi.getUserFollowersList(username).map { it.toFollowerUser() }
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
                val userFollowingList = userDetailApi.getUserFollowingList(username)
                    .map { it.toFollowingUser() }
                emit(Resource.Success(userFollowingList))
            } catch (e: IOException) {
                emit(Resource.Error(message = IO_EXCEPTION_MESSAGE))
            } catch (e: Exception) {
                emit(Resource.Error(message = EXCEPTION_MESSAGE))
            }
        }

    override suspend fun isFavUser(username: String): Boolean {
        val user = coreDao.findFavUser(username)
        return user != null
    }

    override suspend fun insertFavUser(user: User) {
        coreDao.insertFavUser(FavUserEntity.fromUser(user))
    }

    override suspend fun deleteFavUser(user: User) {
        coreDao.deleteFavUser(FavUserEntity.fromUser(user))
    }

    companion object {
        private const val IO_EXCEPTION_MESSAGE = "Please check your internet connection"
        private const val EXCEPTION_MESSAGE = "Something went wrong"
    }
}