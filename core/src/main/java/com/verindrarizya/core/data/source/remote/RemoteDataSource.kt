package com.verindrarizya.core.data.source.remote

import android.util.Log
import com.verindrarizya.core.data.source.remote.network.ApiResponse
import com.verindrarizya.core.data.source.remote.network.ApiService
import com.verindrarizya.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService){

    companion object { private const val TAG = "RemoteDataSource"}

    suspend fun getMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getMovies()
                val dataArray = response.results

                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, e.toString() )
            }
        }.flowOn(Dispatchers.IO)
    }

}