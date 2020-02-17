package com.example.testapplication.dictionary.data

import com.example.testapplication.core.data.BaseRepository
import com.example.testapplication.dictionary.model.Result
import com.example.testapplication.core.exception.AppException
import com.example.testapplication.core.functional.Either
import com.example.testapplication.core.platform.NetworkHandler
import com.example.testapplication.dictionary.data.datasource.LocalDataSource
import com.example.testapplication.dictionary.data.datasource.RemoteDataSource

class ResultRepositoryImpl
    (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val networkHandler: NetworkHandler
) : BaseRepository(), ResultRepository {

    override suspend fun getResult(term: String): Either<AppException, List<Result>> {
        return either {
            //Error handling
            val local = getLocalList(term)
            return@either if (local.isNotEmpty()) {
                local
            } else {
                if (networkHandler.isConnected) getApiList(term)
                    .also { insertResultToDatabase(it) }
                else throw AppException.NetworkError(RuntimeException("No offline data available!"))
            }
        }

    }


    private suspend fun insertResultToDatabase(it: List<Result>) {
        localDataSource.addDictionaryEntity(it.map { result -> result.toDictionaryEntity() })
    }

    private suspend fun getApiList(term: String) = remoteDataSource.getTerms(term).results

    private suspend fun getLocalList(term: String) = localDataSource.getDictionaryEntity(term)
        .map { it.toResult() }
}




