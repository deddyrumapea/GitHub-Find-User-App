package com.romnan.githubfinduser.feature_user_detail.domain.use_case

import com.romnan.githubfinduser.feature_user_detail.domain.repository.UserDetailRepository

class IsFavUser(private val repository: UserDetailRepository) {
    suspend operator fun invoke(username: String): Boolean {
        return repository.isFavUser(username)
    }
}