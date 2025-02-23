package com.example.bstory.base

import android.app.Application
import com.example.bstory.config.AppConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class AppBase : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@AppBase)
            modules(
                AppConfig.koinModules
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}