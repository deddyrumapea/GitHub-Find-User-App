package com.romnan.githubfinduser.feature_search_user.di

import com.romnan.githubfinduser.feature_search_user.data.remote.SearchUsersApi
import com.romnan.githubfinduser.feature_search_user.data.repository.SearchUsersRepositoryImpl
import com.romnan.githubfinduser.feature_search_user.domain.repository.SearchUsersRepository
import com.romnan.githubfinduser.feature_search_user.domain.use_case.SearchUsers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchUserModule {
    @Provides
    @Singleton
    fun provideSearchUsersUseCases(repository: SearchUsersRepository): SearchUsers {
        return SearchUsers(repository)
    }

    @Provides
    @Singleton
    fun provideSearchUsersRepository(api: SearchUsersApi): SearchUsersRepository {
        return SearchUsersRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSearchUsersApi(retrofit: Retrofit): SearchUsersApi {
        return retrofit.create(SearchUsersApi::class.java)
    }
}