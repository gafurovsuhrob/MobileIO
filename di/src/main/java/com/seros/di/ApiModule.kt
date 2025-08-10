package com.seros.di

import com.seros.data.remote.api.AuthApi
import org.koin.dsl.module

val apiModule = module {
    single {
        AuthApi(
            client = get(),
            tokenManager = get()
        )
    }
}
