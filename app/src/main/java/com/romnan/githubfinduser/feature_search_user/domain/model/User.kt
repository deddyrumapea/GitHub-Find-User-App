package com.romnan.githubfinduser.feature_search_user.domain.model

data class User(
    var avatarUrl: String,
    val username: String,
    val type: String
)