package com.seros.domain.di

import org.koin.dsl.module

import com.seros.domain.validator.*

val validatorModule = module {
    factory { RegisterValidator }
    factory { LoginValidator }
    factory { RPValidator }
}