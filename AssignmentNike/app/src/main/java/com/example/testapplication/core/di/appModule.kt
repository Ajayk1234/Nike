package com.example.testapplication.core.di

import com.example.testapplication.core.platform.NetworkHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Main DI module for app
 */
val appModule = module {
    single { NetworkHandler((androidContext())) }
}