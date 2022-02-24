package com.romnan.githubfinduser.feature_search_user.data.repository

import com.romnan.githubfinduser.core.util.Resource
import com.romnan.githubfinduser.feature_search_user.data.remote.SearchUsersApi
import com.romnan.githubfinduser.feature_search_user.domain.model.User
import com.romnan.githubfinduser.feature_search_user.domain.repository.SearchUsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class SearchUsersRepositoryImpl(
    private val api: SearchUsersApi
) : SearchUsersRepository {
    override fun searchUsers(query: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading(emptyList()))
        try {
            val usersList = api.searchUsers(query).items?.map { it.toUser() }
            emit(Resource.Success(usersList))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Please check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Something went wrong"))
        }
    }
}