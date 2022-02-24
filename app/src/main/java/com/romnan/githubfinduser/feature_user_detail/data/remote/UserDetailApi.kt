package com.romnan.githubfinduser.feature_user_detail.data.remote

import com.romnan.githubfinduser.feature_user_detail.data.remote.dto.FollowerUserDto
import com.romnan.githubfinduser.feature_user_detail.data.remote.dto.FollowingUserDto
import com.romnan.githubfinduser.feature_user_detail.data.remote.dto.UserDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserDetailApi {
    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): UserDetailDto

    @GET("users/{username}/followers")
    suspend fun getUserFollowersList(@Path("username") username: String): List<FollowerUserDto>

    @GET("users/{username}/following")
    suspend fun getUserFollowingList(@Path("username") username: String): List<FollowingUserDto>
}