package com.romnan.githubfinduser.feature_search_user.data.remote

import com.romnan.githubfinduser.feature_search_user.data.remote.dto.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchUsersApi {
    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String): SearchResultDto
}