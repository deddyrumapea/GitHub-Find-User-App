package com.romnan.githubfinduser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var avatar: Int,
    val name: String?,
    val username: String?,
    val location: String?,
    val company: String?,
    val repositories: String?,
    val followers: String?,
    val following: String?
) : Parcelable