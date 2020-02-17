package com.example.testapplication

import com.example.testapplication.core.exception.AppException
import com.example.testapplication.core.platform.NetworkHandler
import com.example.testapplication.dictionary.data.ResultRepository
import com.example.testapplication.dictionary.data.ResultRepositoryImpl
import com.example.testapplication.dictionary.data.datasource.LocalDataSource
import com.example.testapplication.dictionary.data.datasource.RemoteDataSource
import com.example.testapplication.dictionary.model.Result
import com.example.testapplication.dictionary.model.TermsResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class RepositoryTest : UnitTest() {


    private val termsResponse = TermsResponse(
        listOf(
            Result(author = "hello", word = "test", thumbsDown = 20, thumbsUp = 50),
            Result(author = "xyz", word = "test", thumbsDown = 20, thumbsUp = 40),
            Result(author = "alpha", word = "test", thumbsDown = 22, thumbsUp = 60),
            Result(author = "beta", word = "test", thumbsDown = 25, thumbsUp = 70)
        )
    )

    private lateinit var resultRepository: ResultRepository
    @Mock
    lateinit var localDataSource: LocalDataSource
    @Mock
    lateinit var remoteDataSource: RemoteDataSource
    @Mock
    private lateinit var networkHandler: NetworkHandler


    @Before
    fun setUp() {
        resultRepository = ResultRepositoryImpl(remoteDataSource, localDataSource, networkHandler)
        runBlocking {
            Mockito.`when`(localDataSource.getDictionaryEntity("test"))
                .thenReturn(arrayListOf())


            Mockito.`when`(remoteDataSource.getTerms("test"))
                .thenReturn(termsResponse)
        }

    }

    @Test
    fun `test loading from source`() {
        Mockito.`when`(networkHandler.isConnected)
            .thenReturn(true)

        runBlocking {
            resultRepository.getResult("test").either({
            }, {
                assert(it.isNotEmpty())
            })
        }
    }

    @Test
    fun `test network error while loading from source`() {
        Mockito.`when`(networkHandler.isConnected)
            .thenReturn(true)

        runBlocking {
            resultRepository.getResult("test").either({
                assert(it is AppException.NetworkError)

            }, {
            })
        }
    }


}