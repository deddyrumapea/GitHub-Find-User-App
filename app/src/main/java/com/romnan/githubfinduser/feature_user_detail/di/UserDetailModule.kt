package com.romnan.githubfinduser.feature_user_detail.di

import com.romnan.githubfinduser.core.data.local.CoreDatabase
import com.romnan.githubfinduser.feature_user_detail.data.remote.UserDetailApi
import com.romnan.githubfinduser.feature_user_detail.data.repository.UserDetailRepositoryImpl
import com.romnan.githubfinduser.feature_user_detail.domain.repository.UserDetailRepository
import com.romnan.githubfinduser.feature_user_detail.domain.use_case.*
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
    fun provideUserDetailUseCases(repository: UserDetailRepository): UserDetailUseCase {
        return UserDetailUseCase(
            getUserDetail = GetUserDetail(repository),
            getUserFollowersList = GetUserFollowersList(repository),
            getUserFollowingList = GetUserFollowingList(repository),
            isFavUser = IsFavUser(repository),
            insertFavUser = InsertFavUser(repository),
            deleteFavUser = DeleteFavUser(repository)
        )
    }

    @Provides
    @Singleton
    fun provideUserDetailRepository(api: UserDetailApi, db: CoreDatabase): UserDetailRepository {
        return UserDetailRepositoryImpl(userDetailApi = api, coreDao = db.dao)
    }

    @Provides
    @Singleton
    fun provideUserDetailApi(retrofit: Retrofit): UserDetailApi {
        return retrofit.create(UserDetailApi::class.java)
    }
}