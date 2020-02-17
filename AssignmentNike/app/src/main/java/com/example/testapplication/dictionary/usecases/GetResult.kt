package com.example.testapplication.dictionary.usecases

import com.example.testapplication.dictionary.data.ResultRepository
import com.example.testapplication.dictionary.model.Result
import com.example.testapplication.core.exception.AppException
import com.example.testapplication.core.functional.Either
import com.example.testapplication.core.interactor.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

/**
 * Suggestion from api
 */
class GetResult constructor(
    private val repository: ResultRepository,
    scope: CoroutineScope,
    dispatcher: CoroutineDispatcher
) : UseCase<List<Result>, GetResult.Param>(scope, dispatcher) {

    override suspend fun run(params: Param): Either<AppException, List<Result>> {
        return repository.getResult(params.term)
    }

    data class Param(val term: String)
}