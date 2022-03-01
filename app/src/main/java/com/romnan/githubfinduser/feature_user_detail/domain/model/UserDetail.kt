package com.romnan.githubfinduser.feature_user_detail.domain.model

import com.romnan.githubfinduser.core.domain.model.User

data class UserDetail(
    val avatarUrl: String,
    val name: String,
    val username: String,
    val location: String,
    val company: String,
    val repositoriesCount: Int,
    val followersCount: Int,
    val followingCount: Int,
    val type: String
) {
    fun toUser() = User(
        avatarUrl = avatarUrl,
        username = username,
        type = type
    )
}