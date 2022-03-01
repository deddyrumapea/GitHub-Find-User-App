package com.romnan.githubfinduser.feature_fav_users_list.domain.repository

import com.romnan.githubfinduser.core.domain.model.User
import com.romnan.githubfinduser.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface FavUsersListRepository {
    fun findAllFavUsers(): Flow<Resource<List<User>>>
}