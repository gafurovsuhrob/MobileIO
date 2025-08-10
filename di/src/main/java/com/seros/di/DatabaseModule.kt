package com.seros.di

import android.app.Application
import androidx.room.Room
import com.seros.data.local.AppDatabase
import com.seros.data.local.dao.UserDao
import org.koin.dsl.module

val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get<Application>(),
            AppDatabase::class.java,
            "mobile_io.db"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    single<UserDao> {
        get<AppDatabase>().userDao()
    }
}