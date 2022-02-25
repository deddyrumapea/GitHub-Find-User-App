package com.romnan.githubfinduser.feature_user_detail.domain.use_case

import com.romnan.githubfinduser.core.domain.model.User
import com.romnan.githubfinduser.feature_user_detail.domain.repository.UserDetailRepository

class DeleteFavUser(private val repository: UserDetailRepository) {
    suspend operator fun invoke(user: User) {
        repository.deleteFavUser(user)
    }
}