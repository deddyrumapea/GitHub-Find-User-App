package com.romnan.githubfinduser.feature_fav_users_list.domain.use_case

import com.romnan.githubfinduser.core.domain.model.User
import com.romnan.githubfinduser.core.domain.util.Resource
import com.romnan.githubfinduser.feature_fav_users_list.domain.repository.FavUsersListRepository
import kotlinx.coroutines.flow.Flow

class FindAllFavUsers(private val repository: FavUsersListRepository) {
    operator fun invoke(): Flow<Resource<List<User>>> = repository.findAllFavUsers()
}