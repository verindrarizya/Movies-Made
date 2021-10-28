package com.verindrarizya.core.data.source.remote

import com.verindrarizya.core.data.source.remote.network.ApiResponse
import com.verindrarizya.core.data.source.remote.network.ApiService
import com.verindrarizya.core.util.RemoteDataDummy
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
        `when`(apiService.getMovies()).thenReturn(RemoteDataDummy.listResponses)

        val result = remoteDataSource.getMovies().first()
        verify(apiService).getMovies()

        assertEquals(ApiResponse.Success(RemoteDataDummy.listResponses.results), result)
    }

    @Test
    fun test_getMovies_returnEmpty(): Unit = runBlocking {
        `when`(apiService.getMovies()).thenReturn(RemoteDataDummy.emptyListResponse)

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