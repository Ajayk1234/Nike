package com.example.testapplication.core.extensions.context

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.testapplication.core.exception.AppException


fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<AppException>> LifecycleOwner.failure(
    liveData: L,
    body: (AppException?) -> Unit
) =
    liveData.observe(this, Observer(body))