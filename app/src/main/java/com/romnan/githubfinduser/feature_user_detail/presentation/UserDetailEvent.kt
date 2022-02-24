package com.romnan.githubfinduser.feature_user_detail.presentation

sealed class UserDetailEvent {
    data class UsernameReceived(val username: String) : UserDetailEvent()
}