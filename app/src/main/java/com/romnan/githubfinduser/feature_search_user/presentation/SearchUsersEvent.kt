package com.romnan.githubfinduser.feature_search_user.presentation

sealed class SearchUsersEvent {
    data class QueryTextSubmit(val query: String): SearchUsersEvent()
}