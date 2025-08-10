package com.seros.di

import com.seros.presentation.ui.auth.forgot_password.RPViewModel
import com.seros.presentation.ui.auth.login.LoginViewModel
import com.seros.presentation.ui.auth.register.RegisterViewModel
import com.seros.presentation.ui.main.MainViewModel
import com.seros.presentation.ui.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { RPViewModel(get()) }
    viewModel {
        MainViewModel(
            get(), get(), get()
        )
    }
}