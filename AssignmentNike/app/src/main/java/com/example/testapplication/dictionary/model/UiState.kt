package com.example.testapplication.dictionary.model

import com.example.testapplication.core.exception.AppException

sealed class UiState {
    data class Success(val result: List<ResultView>) : UiState()
    data class Error(val error: AppException) : UiState()
    object Loading : UiState()
}