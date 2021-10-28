package com.verindrarizya.core.data.source.local

import com.verindrarizya.core.data.source.local.entity.MovieEntity
import com.verindrarizya.core.data.source.local.room.MovieDao
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

    private val listMovieEntity: MutableList<MovieEntity> = mutableListOf(
        MovieEntity(
            1,
            "Movie1 title",
            "Movie1 poster",
            "Movie1 date",
            9.00,
            "Movie1 overview",
            false
        ),
        MovieEntity(
            2,
            "Movie2 title",
            "Movie2 poster",
            "Movie2 date",
            9.50,
            "Movie2 overview",
            false
        ),
        MovieEntity(
            3,
            "Movie3 title",
            "Movie3 poster",
            "Movie3 date",
            8.00,
            "Movie3 overview",
            true
        )
    )

    private val newMovieEntity = MovieEntity(
        4,
        "Movie4 title",
        "Movie4 poster",
        "Movie4 date",
        8.50,
        "Movie4 overview",
        false
    )

    private val newMovies = listOf(newMovieEntity)

    @Mock
    private lateinit var movieDao: MovieDao

    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = LocalDataSource(movieDao)
    }

    @Test
    fun getMovies(): Unit = runBlocking {
        `when`(movieDao.getMovies()).thenReturn(flowOf(listMovieEntity))

        val result = localDataSource.getMovies().first()
        verify(movieDao).getMovies()

        assertEquals(listMovieEntity, result)
    }

    @Test
    fun getMovie(): Unit = runBlocking {
        val movieId = 3
        val movie: MovieEntity = listMovieEntity.find { it.id == movieId } ?: throw Exception("MovieEntity not found")

        `when`(movieDao.getMovie(movieId)).thenReturn(flowOf(movie))

        val result = localDataSource.getMovie(movieId).first()
        verify(movieDao).getMovie(movieId)

        assertEquals(movie, result)
    }

    @Test
    fun getFavoriteMovies(): Unit = runBlocking {
        val favoriteList = listMovieEntity.filter { it.isFavorite == true }

        `when`(movieDao.getFavoriteMovies()).thenReturn(flowOf(favoriteList))

        val result = movieDao.getFavoriteMovies().first()
        verify(movieDao).getFavoriteMovies()

        assertEquals(favoriteList, result)
    }

    @Test
    fun insertMovie(): Unit = runBlocking {
        `when`(movieDao.insertMovie(newMovies)).then {
            newMovies.forEach { listMovieEntity.add(it) }
            true
        }

        localDataSource.insertMovie(newMovies)
        verify(movieDao).insertMovie(newMovies)

        assertEquals(true, listMovieEntity.containsAll(newMovies))
    }

    @Test
    fun setFavoriteMovie_shouldBeTrue(): Unit = runBlocking {
        val movie = listMovieEntity[0]

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
        val movie = listMovieEntity[0]
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