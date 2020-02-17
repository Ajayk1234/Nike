package com.example.testapplication.dictionary.data.datasource

import com.example.testapplication.dictionary.model.TermsResponse
import retrofit2.http.*

interface DictionaryApiService {
    @Headers(
        "x-rapidapi-host: mashape-community-urban-dictionary.p.rapidapi.com",
        "x-rapidapi-key: d325dbbf2fmshb180e029efedd97p1759cfjsnf089ec08cce6"
    )
    @GET("define")
    suspend fun getTerms(@Query("term") term: String): TermsResponse
}