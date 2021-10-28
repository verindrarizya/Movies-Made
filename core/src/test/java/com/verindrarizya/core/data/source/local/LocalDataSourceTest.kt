package com.verindrarizya.core.data.source.local

import com.verindrarizya.core.data.source.local.entity.MovieEntity
import com.verindrarizya.core.data.source.local.room.MovieDao
import com.verindrarizya.core.util.LocalDataDummy
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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
class LocalDataSourceTest {

    @Mock
    private lateinit var movieDao: MovieDao

    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = LocalDataSource(movieDao)
    }

    @Test
    fun getMovies(): Unit = runBlocking {
        `when`(movieDao.getMovies()).thenReturn(flowOf(LocalDataDummy.listMovieEntity))

        val result = localDataSource.getMovies().first()
        verify(movieDao).getMovies()

        assertEquals(LocalDataDummy.listMovieEntity, result)
    }

    @Test
    fun getMovie(): Unit = runBlocking {
        val movieId = 3
        val movie: MovieEntity = LocalDataDummy.listMovieEntity
                                    .find { it.id == movieId } ?: throw Exception("MovieEntity not found")

        `when`(movieDao.getMovie(movieId)).thenReturn(flowOf(movie))

        val result = localDataSource.getMovie(movieId).first()
        verify(movieDao).getMovie(movieId)

        assertEquals(movie, result)
    }

    @Test
    fun getFavoriteMovies(): Unit = runBlocking {
        val favoriteList = LocalDataDummy.listMovieEntity
                                .filter { it.isFavorite == true }

        `when`(movieDao.getFavoriteMovies()).thenReturn(flowOf(favoriteList))

        val result = movieDao.getFavoriteMovies().first()
        verify(movieDao).getFavoriteMovies()

        assertEquals(favoriteList, result)
    }

    @Test
    fun insertMovie(): Unit = runBlocking {
        `when`(movieDao.insertMovie(LocalDataDummy.newMovies)).then {
            LocalDataDummy.newMovies.forEach { LocalDataDummy.listMovieEntity.add(it) }
            true
        }

        localDataSource.insertMovie(LocalDataDummy.newMovies)
        verify(movieDao).insertMovie(LocalDataDummy.newMovies)

        assertEquals(true, LocalDataDummy.listMovieEntity.containsAll(LocalDataDummy.newMovies))
    }

    @Test
    fun setFavoriteMovie_shouldBeTrue(): Unit = runBlocking {
        val movie = LocalDataDummy.listMovieEntity[0]

        `when`(movieDao.updateFavoriteMovie(movie)).then {
            movie.isFavorite = true
            println(movie.isFavorite)
            true
        }

        localDataSource.setFavoriteMovie(movie)
        verify(movieDao).updateFavoriteMovie(movie)

        assertEquals(true, movie.isFavorite)
    }

    @Test
    fun setFavoriteMovie_shouldBeFalse(): Unit = runBlocking {
        val movie = LocalDataDummy.listMovieEntity[0]
        movie.isFavorite = true

        `when`(movieDao.updateFavoriteMovie(movie)).then {
            movie.isFavorite = false
            true
        }

        localDataSource.setFavoriteMovie(movie)
        verify(movieDao).updateFavoriteMovie(movie)

         assertEquals(false, movie.isFavorite)
    }
}