package com.seros.mobileio.di

import android.app.Application
import com.seros.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class DiApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DiApp)
            modules(appModules)
        }
    }
}
