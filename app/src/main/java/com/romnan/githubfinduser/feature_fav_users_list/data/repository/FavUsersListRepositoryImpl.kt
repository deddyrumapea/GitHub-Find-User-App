package com.romnan.githubfinduser.feature_fav_users_list.data.repository

import com.romnan.githubfinduser.core.data.local.CoreDao
import com.romnan.githubfinduser.core.domain.model.User
import com.romnan.githubfinduser.core.domain.util.Resource
import com.romnan.githubfinduser.feature_fav_users_list.domain.repository.FavUsersListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavUsersListRepositoryImpl(
    private val coreDao: CoreDao
) : FavUsersListRepository {
    override fun findAllFavUsers(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading(data = emptyList()))
        try {
            val favUsers = coreDao.findAllFavUsers().map { it.toUser() }
            emit(Resource.Success(favUsers))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Something went wrong"))
        }
    }
}