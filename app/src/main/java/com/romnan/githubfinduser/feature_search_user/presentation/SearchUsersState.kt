package com.romnan.githubfinduser.feature_search_user.presentation

import com.romnan.githubfinduser.core.domain.model.User

data class SearchUsersState(
    val usersList: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)