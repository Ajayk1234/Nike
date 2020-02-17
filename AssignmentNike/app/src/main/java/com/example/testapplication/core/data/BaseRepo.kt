package com.example.testapplication.core.data

import com.example.testapplication.core.exception.AppException
import com.example.testapplication.core.functional.Either

open class BaseRepository {
    suspend fun <R> either(
        data: suspend () -> R
    ): Either<AppException, R> {
        return try {
            Either.Right(
                data.invoke()
            )
        } catch (e: Exception) {
            return Either.Left(AppException.NetworkError(RuntimeException("Network issues")))
        }
    }
}