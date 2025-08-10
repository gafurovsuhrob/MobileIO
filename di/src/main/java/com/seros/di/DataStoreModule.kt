package com.seros.di

import android.content.Context
import com.seros.data.local.manager.TokenManager
import com.seros.data.local.manager.TokenManagerImpl
import org.koin.dsl.module

val dataStoreModule = module {
    single<TokenManager> { TokenManagerImpl(get()) }
}
