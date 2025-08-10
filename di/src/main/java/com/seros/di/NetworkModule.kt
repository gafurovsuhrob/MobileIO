package com.seros.di

import com.seros.data.system.NetworkChecker
import com.seros.data.remote.client.NetworkClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module


val networkModule = module {
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    single {
        NetworkChecker(
            context = get()
        )
    }

    single {
        NetworkClient(
            httpClient = get(),
            tokenManager = get()
        )
    }
}
