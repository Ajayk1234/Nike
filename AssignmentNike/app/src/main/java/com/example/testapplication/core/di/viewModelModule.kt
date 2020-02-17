package com.example.testapplication.core.di

import com.example.testapplication.dictionary.DictionaryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Presentation layers View models DI
 */
val viewModelModule = module {
    viewModel {
        DictionaryViewModel(get())
    }
}