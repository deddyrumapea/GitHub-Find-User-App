package com.romnan.githubfinduser.feature_fav_users_list.di

import com.romnan.githubfinduser.core.data.local.CoreDatabase
import com.romnan.githubfinduser.feature_fav_users_list.data.repository.FavUsersListRepositoryImpl
import com.romnan.githubfinduser.feature_fav_users_list.domain.repository.FavUsersListRepository
import com.romnan.githubfinduser.feature_fav_users_list.domain.use_case.FavUsersListUseCase
import com.romnan.githubfinduser.feature_fav_users_list.domain.use_case.FindAllFavUsers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavUsersListModule {
    @Provides
    @Singleton
    fun provideFavUsersListUseCases(repository: FavUsersListRepository): FavUsersListUseCase {
        return FavUsersListUseCase(FindAllFavUsers(repository))
    }

    @Provides
    @Singleton
    fun provideFavUsersListRepository(db: CoreDatabase): FavUsersListRepository {
        return FavUsersListRepositoryImpl(coreDao = db.dao)
    }
}