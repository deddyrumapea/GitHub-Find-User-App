package com.romnan.githubfinduser.feature_fav_users_list.data.repository

import com.romnan.githubfinduser.core.domain.model.User
import com.romnan.githubfinduser.core.domain.util.Resource
import com.romnan.githubfinduser.feature_fav_users_list.domain.repository.FavUsersListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFavUsersListRepository(val favUsersList: List<User>) : FavUsersListRepository {

    override fun findAllFavUsers(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Success(favUsersList))
    }
}