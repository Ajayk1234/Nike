package com.example.testapplication.dictionary.data

import com.example.testapplication.dictionary.model.Result
import com.example.testapplication.core.exception.AppException
import com.example.testapplication.core.functional.Either

interface ResultRepository {
    suspend fun getResult(term: String): Either<AppException, List<Result>>
}