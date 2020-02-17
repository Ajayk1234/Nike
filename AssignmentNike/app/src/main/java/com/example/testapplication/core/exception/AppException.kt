package com.example.testapplication.core.exception

/**
 * Custom Exceptions
 */
sealed class AppException(val throwable: Throwable) : Exception() {
    class HttpError(throwable: Throwable) : AppException(throwable)
    class NetworkError(throwable: Throwable = Throwable("Server Error!")) : AppException(throwable)
}