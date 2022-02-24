package com.romnan.githubfinduser.feature_user_detail.presentation

import com.romnan.githubfinduser.feature_user_detail.domain.model.UserDetail

data class UserDetailState(
    val userDetail: UserDetail? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)