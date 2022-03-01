package com.romnan.githubfinduser.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.romnan.githubfinduser.core.data.local.entity.FavUserEntity

@Database(
    entities = [FavUserEntity::class],
    version = 1
)
abstract class CoreDatabase : RoomDatabase() {
    abstract val dao: CoreDao

    companion object {
        const val NAME = "db_gfu_core"
    }
}