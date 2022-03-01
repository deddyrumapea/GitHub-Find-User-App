package com.romnan.githubfinduser.feature_fav_users_list.presentation

import com.romnan.githubfinduser.core.domain.model.User

data class FavUsersListState(
    val usersList: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)