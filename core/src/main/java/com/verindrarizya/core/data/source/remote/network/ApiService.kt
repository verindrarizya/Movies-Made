package com.verindrarizya.core.data.source.remote.network

import com.verindrarizya.core.BuildConfig.API_KEY
import com.verindrarizya.core.data.source.remote.response.ListResponses
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/top_rated")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): ListResponses

}