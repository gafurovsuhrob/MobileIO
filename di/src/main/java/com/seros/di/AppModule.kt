package com.seros.di

import com.seros.domain.di.useCaseModule
import com.seros.domain.di.validatorModule
import org.koin.core.module.Module

val appModules: List<Module> = listOf(
    dataStoreModule,
    networkModule,
    apiModule,
    repositoryModule,
    useCaseModule,
    viewModelModule,
    databaseModule,
    validatorModule
)