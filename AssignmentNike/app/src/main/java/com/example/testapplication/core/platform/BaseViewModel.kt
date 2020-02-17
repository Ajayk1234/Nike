package com.example.testapplication.core.platform

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), KoinComponent, CoroutineScope {


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override val coroutineContext: CoroutineContext
        get() = viewModelJob

    override fun onCleared() {
        viewModelJob.cancel()
        uiScope.coroutineContext.cancelChildren()
        super.onCleared()
    }
}