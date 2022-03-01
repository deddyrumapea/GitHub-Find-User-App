package com.romnan.githubfinduser.core.util

import com.romnan.githubfinduser.core.domain.model.User
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowerUser
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowingUser
import com.romnan.githubfinduser.feature_user_detail.domain.model.UserDetail
import io.github.serpro69.kfaker.Faker
import kotlin.random.Random
import kotlin.random.nextUInt

object DataFaker {
    // TODO: delete Faker
    private val faker = Faker()

    init {
        faker.unique.configuration {
            enable(faker::name)
        }
    }

    fun getQueriesResults(
        queriesCount: Int = 20,
        resultsMaxCount: Int = 20
    ): Map<String, List<User>> {
        val data = mutableMapOf<String, List<User>>()

        for (i in 1..queriesCount) {
            val query = faker.name.name()

            val results = mutableListOf<User>()
            val resultsCount = Random.nextInt(until = resultsMaxCount + 1)
            for (j in 1..resultsCount) {
                results.add(getRandomUser())
            }

            data[query] = results
        }
        return data
    }

    private fun getRandomUser() = User(
        avatarUrl = "https://avatars.githubusercontent.com/u/${Random.nextUInt()}?v=4",
        username = "${faker.name.firstName()}${Random.nextUInt()}",
        type = if (Random.nextBoolean()) "User" else "Organization"
    )

    fun getUniqueUsers(count: Int = 20): List<User> {
        val data = mutableListOf<User>()
        for (i in 1..count) {
            data.add(getRandomUser())
        }
        return data
    }

    fun getUniqueUsernames(count: Int = 20): MutableList<String> {
        val data = mutableListOf<String>()
        for (i in 1..count) {
            data.add("${faker.name.firstName()}_${Random.nextUInt()}_$i")
        }
        return data
    }

    fun getUsernameFollowing(
        usernamesCount: Int = 20,
        followingMaxCount: Int = 20
    ): Map<String, List<FollowingUser>> {

        val data = mutableMapOf<String, List<FollowingUser>>()

        for (i in 1..usernamesCount) {
            val username = "${faker.name.firstName()}_$i"
            val followingList = mutableListOf<FollowingUser>()

            val followingCount = Random.nextInt(followingMaxCount + 1)
            for (j in 1..followingCount) {
                followingList.add(
                    FollowingUser(
                        avatarUrl = "https://avatars.githubusercontent.com/u/$i$j?v=4",
                        username = "${faker.name.firstName()}_$i$j",
                        type = if (Random.nextBoolean()) "User" else "Organization"
                    )
                )
            }
            data[username] = followingList
        }
        return data
    }

    fun getUsernameFollowers(
        usernamesCount: Int = 20,
        followersMaxCount: Int = 20
    ): Map<String, List<FollowerUser>> {

        val data = mutableMapOf<String, List<FollowerUser>>()

        for (i in 1..usernamesCount) {
            val username = "${faker.name.firstName()}_$i"
            val followersList = mutableListOf<FollowerUser>()

            val followersCount = Random.nextInt(followersMaxCount + 1)
            for (j in 1..followersCount) {
                followersList.add(
                    FollowerUser(
                        avatarUrl = "https://avatars.githubusercontent.com/u/$i$j?v=4",
                        username = "${faker.name.firstName()}_$i$j",
                        type = if (Random.nextBoolean()) "User" else "Organization"
                    )
                )
            }

            data[username] = followersList
        }
        return data
    }

    fun getUserDetailsList(): List<UserDetail> {
        val data = mutableListOf<UserDetail>()
        for (i in 1..20) {
            data.add(
                UserDetail(
                    avatarUrl = "https://avatars.githubusercontent.com/u/${(faker.phoneNumber)}?v=4",
                    name = faker.name.name(),
                    username = faker.name.firstName(),
                    location = faker.address.city(),
                    company = faker.company.name(),
                    repositoriesCount = (0..100).random(),
                    followingCount = (0..100).random(),
                    followersCount = (0..100).random(),
                    type = if (Random.nextBoolean()) "User" else "Organization"
                )
            )
        }
        return data
    }
}