package com.example.testapplication.core.di

import com.example.testapplication.core.platform.NetworkHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Application module for dependency injection
 */
val mainModule = module {
    single { NetworkHandler((androidContext())) }
}