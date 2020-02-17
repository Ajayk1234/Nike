package com.example.testapplication

import android.app.Application
import com.example.testapplication.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //Init koin (dependency Injection frame for kotlin)
        startKoin {
            androidContext(this@TestApplication.applicationContext)
            androidLogger(Level.DEBUG)
            modules(
                listOf(
                    viewModelModule,
                    apiModule,
                    dataModule,
                    mainModule,
                    databaseModule,
                    useCaseModule
                )
            )
        }
    }
}