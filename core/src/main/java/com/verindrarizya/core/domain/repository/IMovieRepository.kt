package com.verindrarizya.core.domain.repository

import com.verindrarizya.core.data.Resource
import com.verindrarizya.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getMovies(): Flow<Resource<List<Movie>>>

    fun getMovie(id: Int): Flow<Movie>

    fun getFavoriteMovies(): Flow<List<Movie>>

    suspend fun setFavoriteMovie(movie: Movie)

}