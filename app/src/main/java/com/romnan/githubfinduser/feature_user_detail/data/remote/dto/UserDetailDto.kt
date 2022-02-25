package com.romnan.githubfinduser.feature_user_detail.data.remote.dto

import com.romnan.githubfinduser.feature_user_detail.domain.model.UserDetail

data class UserDetailDto(
    val avatar_url: String?,
    val company: String?,
    val followers: Int?,
    val following: Int?,
    val location: String?,
    val login: String?,
    val name: String?,
    val public_repos: Int?,
    val type: String?
) {
    fun toUserDetail() = UserDetail(
        avatarUrl = avatar_url ?: "",
        name = name ?: "",
        username = login ?: "",
        location = location ?: "",
        company = company ?: "",
        repositoriesCount = public_repos ?: 0,
        followersCount = followers ?: 0,
        followingCount = following ?: 0,
        type = type ?: ""
    )
}