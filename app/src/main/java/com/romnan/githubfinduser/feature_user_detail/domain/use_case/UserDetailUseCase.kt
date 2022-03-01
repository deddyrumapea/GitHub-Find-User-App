package com.romnan.githubfinduser.feature_user_detail.domain.use_case

data class UserDetailUseCase(
    val getUserDetail: GetUserDetail,
    val getUserFollowersList: GetUserFollowersList,
    val getUserFollowingList: GetUserFollowingList,
    val isFavUser: IsFavUser,
    val insertFavUser: InsertFavUser,
    val deleteFavUser: DeleteFavUser
)
