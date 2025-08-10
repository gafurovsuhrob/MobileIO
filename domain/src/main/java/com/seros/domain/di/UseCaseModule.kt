package com.seros.domain.di

import com.seros.domain.usecase.*
import org.koin.dsl.module

val useCaseModule = module {
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { RecoverPasswordUseCase(get()) }
    factory { GetOfflineUserUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { CheckUserLoggedInUseCase(get()) }
    factory { LoginLocalUseCase(get()) }
    factory { LogoutLocalUseCase(get()) }
}
