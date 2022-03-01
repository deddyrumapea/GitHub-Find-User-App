package com.romnan.githubfinduser.core.domain.model

data class User(
    var avatarUrl: String,
    val username: String,
    val type: String
)