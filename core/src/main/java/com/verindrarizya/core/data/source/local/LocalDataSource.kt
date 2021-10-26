package com.verindrarizya.core.data.source.local

import com.verindrarizya.core.data.source.local.entity.MovieEntity
import com.verindrarizya.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {

    fun getMovies(): Flow<List<MovieEntity>> = movieDao.getMovies()

    fun getMovie(id: Int): Flow<MovieEntity> = movieDao.getMovie(id)

    fun getFavoriteMovies(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()

    suspend fun insertMovie(movies: List<MovieEntity>) = movieDao.insertMovie(movies)

    suspend fun setFavoriteMovie(movie: MovieEntity) {
        movie.isFavorite = !movie.isFavorite
        movieDao.updateFavoriteMovie(movie)
    }
}