package com.romnan.githubfinduser.feature_user_detail.data.remote.dto

import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowerUser

data class FollowerUserDto(
    val avatar_url: String?,
    val login: String?,
    val type: String?,
) {
    fun toFollowerUser() = FollowerUser(
        avatarUrl = avatar_url ?: "",
        username = login ?: "",
        type = type ?: ""
    )
}