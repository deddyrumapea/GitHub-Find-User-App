package com.romnan.githubfinduser.feature_user_detail.domain.use_case

data class UserDetailUseCases(
    val getUserDetail: GetUserDetail,
    val getUserFollowersList: GetUserFollowersList,
    val getUserFollowingList: GetUserFollowingList
)
