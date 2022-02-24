package com.romnan.githubfinduser.feature_user_detail.presentation.following_list

import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowingUser

data class FollowingListState(
    val usersList: List<FollowingUser> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)