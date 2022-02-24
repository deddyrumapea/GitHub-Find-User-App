package com.romnan.githubfinduser.feature_search_user.data.remote.dto

import com.romnan.githubfinduser.feature_search_user.domain.model.User

data class UserDto(
    val avatar_url: String?,
    val login: String?,
    val type: String?,
) {
    fun toUser() = User(
        avatarUrl = avatar_url ?: "",
        username = login ?: "",
        type = type ?: ""
    )
}