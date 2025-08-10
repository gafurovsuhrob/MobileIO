package com.seros.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seros.data.local.dao.UserDao
import com.seros.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}