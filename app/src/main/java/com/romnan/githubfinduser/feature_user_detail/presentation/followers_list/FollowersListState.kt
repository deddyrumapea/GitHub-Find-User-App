package com.romnan.githubfinduser.feature_user_detail.presentation.followers_list

import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowerUser

data class FollowersListState(
    val usersList: List<FollowerUser> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)