package com.romnan.githubfinduser.feature_user_detail.domain.model

data class UserDetail(
    val avatarUrl: String,
    val name: String,
    val username: String,
    val location: String,
    val company: String,
    val repositoriesCount: Int,
    val followersCount: Int,
    val followingCount: Int
)
