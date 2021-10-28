package com.verindrarizya.core.data

import com.verindrarizya.core.data.source.local.LocalDataSource
import com.verindrarizya.core.data.source.local.entity.MovieEntity
import com.verindrarizya.core.data.source.remote.RemoteDataSource
import com.verindrarizya.core.data.source.remote.network.ApiResponse
import com.verindrarizya.core.domain.model.Movie
import com.verindrarizya.core.domain.repository.IMovieRepository
import com.verindrarizya.core.util.LocalDataDummy
import com.verindrarizya.core.util.RemoteDataDummy
import com.verindrarizya.core.utils.DataMapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    private val localDataSource: LocalDataSource = mock(LocalDataSource::class.java)
    private val remoteDataSource: RemoteDataSource = mock(RemoteDataSource::class.java)

    private val movieRepository: IMovieRepository = MovieRepository(remoteDataSource, localDataSource)

    @Test
    fun getMovies(): Unit = runBlocking {
        `when`(localDataSource.getMovies()).thenReturn(flowOf(LocalDataDummy.listMovieEntity))
        `when`(remoteDataSource.getMovies()).thenReturn(flowOf(ApiResponse.Success(RemoteDataDummy.listResponses.results)))

        val result = movieRepository.getMovies().drop(2).first()

        assertEquals(
            Resource.Succcess(DataMapper.mapEntitiesToDomain(LocalDataDummy.listMovieEntity)).data,
            result.data
        )
    }

    @Test
    fun getMovie(): Unit = runBlocking {
        val movieId = 2
        val movie = LocalDataDummy.listMovieEntity.find { it.id == movieId }!!

        val expectedValue = DataMapper.mapEntityToDomain(movie)

        `when`(localDataSource.getMovie(movieId)).thenReturn(flowOf(movie))

        val result = movieRepository.getMovie(movieId).first()
        verify(localDataSource).getMovie(movieId)

        assertEquals(expectedValue, result)
    }

    @Test
    fun getFavoriteMovies(): Unit = runBlocking {
        `when`(localDataSource.getFavoriteMovies()).thenReturn(flowOf(LocalDataDummy.listMovieEntity))

        val result = movieRepository.getFavoriteMovies().first()
        verify(localDataSource).getFavoriteMovies()

        val expectedValues = DataMapper.mapEntitiesToDomain(LocalDataDummy.listMovieEntity)
        assertEquals(expectedValues, result)
    }

    @Test
    fun setFavoriteMovie(): Unit = runBlocking {
        val newMovieDomain = Movie(
            99,
            "new movie title",
            "new movie poster",
            "new movie date",
            9.00,
            "new movie overview",
            false
        )
        val newMovieEntity = DataMapper.mapDomainToEntity(newMovieDomain)

        doAnswer { invocation ->
            val arg1 = invocation.getArgument(0) as MovieEntity

            assertEquals(newMovieEntity, arg1)
            null
        }.`when`(localDataSource).setFavoriteMovie(newMovieEntity)

        movieRepository.setFavoriteMovie(newMovieDomain)
        verify(localDataSource).setFavoriteMovie(newMovieEntity)
    }
}