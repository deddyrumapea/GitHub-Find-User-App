package com.romnan.githubfinduser.feature_user_detail.data.remote.dto

import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowingUser

data class FollowingUserDto(
    val avatar_url: String?,
    val login: String?,
    val type: String?,
) {
    fun toFollowingUser() = FollowingUser(
        avatarUrl = avatar_url ?: "",
        username = login ?: "",
        type = type ?: ""
    )
}