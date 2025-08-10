package com.seros.di

import com.seros.domain.di.useCaseModule
import org.koin.core.module.Module

val appModules: List<Module> = listOf(
    dataStoreModule,
    networkModule,
    apiModule,
    repositoryModule,
    useCaseModule,
    viewModelModule,
    databaseModule
)