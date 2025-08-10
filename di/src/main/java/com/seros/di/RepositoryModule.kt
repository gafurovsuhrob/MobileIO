package com.seros.di

import com.seros.data.local.manager.TokenManager
import com.seros.data.local.manager.TokenManagerImpl
import com.seros.data.repository.AuthRepositoryImpl
import com.seros.domain.repository.AuthRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            api = get(),
            userDao = get(),
            tokenManager = get(),
            networkChecker = get()
        )
    }

    single<TokenManager> {
        TokenManagerImpl(
            context = get()
        )
    }
}
