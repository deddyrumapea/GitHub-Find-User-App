package com.romnan.githubfinduser.feature_user_detail.di

import com.romnan.githubfinduser.feature_user_detail.data.remote.UserDetailApi
import com.romnan.githubfinduser.feature_user_detail.data.repository.UserDetailRepositoryImpl
import com.romnan.githubfinduser.feature_user_detail.domain.repository.UserDetailRepository
import com.romnan.githubfinduser.feature_user_detail.domain.use_case.GetUserDetail
import com.romnan.githubfinduser.feature_user_detail.domain.use_case.GetUserFollowersList
import com.romnan.githubfinduser.feature_user_detail.domain.use_case.GetUserFollowingList
import com.romnan.githubfinduser.feature_user_detail.domain.use_case.UserDetailUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDetailModule {
    @Provides
    @Singleton
    fun provideUserDetailUseCases(repository: UserDetailRepository): UserDetailUseCases {
        return UserDetailUseCases(
            getUserDetail = GetUserDetail(repository),
            getUserFollowersList = GetUserFollowersList(repository),
            getUserFollowingList = GetUserFollowingList(repository)
        )
    }

    @Provides
    @Singleton
    fun provideUserDetailRepository(api: UserDetailApi): UserDetailRepository {
        return UserDetailRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUserDetailApi(retrofit: Retrofit): UserDetailApi {
        return retrofit.create(UserDetailApi::class.java)
    }
}