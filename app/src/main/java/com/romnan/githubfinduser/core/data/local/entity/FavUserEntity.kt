package com.romnan.githubfinduser.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.romnan.githubfinduser.core.domain.model.User

@Entity
data class FavUserEntity(
    @PrimaryKey val username: String,
    var avatarUrl: String,
    val type: String
) {
    fun toUser() = User(
        avatarUrl = avatarUrl,
        username = username,
        type = type
    )

    companion object {
        fun fromUser(user: User) = FavUserEntity(
            username = user.username,
            avatarUrl = user.avatarUrl,
            type = user.type
        )
    }
}