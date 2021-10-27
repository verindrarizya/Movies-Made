package com.verindrarizya.core.data.source.remote

import com.verindrarizya.core.data.source.remote.network.ApiResponse
import com.verindrarizya.core.data.source.remote.network.ApiService
import com.verindrarizya.core.data.source.remote.response.ListResponses
import com.verindrarizya.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceTest {

    private val listResponses = ListResponses(listOf(
        MovieResponse(
            "movie1 overview",
            "movie1 releaseDate",
            199.0,
            1,
            "movie1 title",
            "movie1 posterPath"
        ),
        MovieResponse(
            "movie2 overview",
            "movie2 releaseDate",
            299.0,
            2,
            "movie2 title",
        "movie2 posterPath"
        )
    ))

    private val emptyListResponse = ListResponses(listOf())

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        apiService
        remoteDataSource = RemoteDataSource(apiService)
    }


    @Test
    fun test_getMovies_returnSuccess(): Unit = runBlocking {
        `when`(apiService.getMovies()).thenReturn(listResponses)

        val result = remoteDataSource.getMovies().first()
        verify(apiService).getMovies()

        assertEquals(ApiResponse.Success(listResponses.results), result)
    }

    @Test
    fun test_getMovies_returnEmpty(): Unit = runBlocking {
        `when`(apiService.getMovies()).thenReturn(emptyListResponse)

        val result = remoteDataSource.getMovies().first()
        verify(apiService).getMovies()

        assertEquals(ApiResponse.Empty, result)
    }

    @Test
    fun test_getMovies_returnError(): Unit = runBlocking {
        val exceptionMessage = "Exception Thrown"
        val exception = RuntimeException(exceptionMessage)

        println(exception)

        `when`(apiService.getMovies()).thenThrow(RuntimeException(exceptionMessage))

        val result = remoteDataSource.getMovies().first()
        verify(apiService).getMovies()

        assertEquals(ApiResponse.Error(exception.toString()), result)
    }
}