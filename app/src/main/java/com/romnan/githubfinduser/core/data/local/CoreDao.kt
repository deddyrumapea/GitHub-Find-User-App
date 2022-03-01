package com.romnan.githubfinduser.core.data.local

import androidx.room.*
import com.romnan.githubfinduser.core.data.local.entity.FavUserEntity

@Dao
interface CoreDao {
    @Query("SELECT * FROM favuserentity")
    suspend fun findAllFavUsers(): List<FavUserEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertFavUser(user: FavUserEntity)

    @Delete
    suspend fun deleteFavUser(user: FavUserEntity)

    @Query("SELECT * FROM favuserentity WHERE username = :username")
    suspend fun findFavUser(username: String): FavUserEntity?
}