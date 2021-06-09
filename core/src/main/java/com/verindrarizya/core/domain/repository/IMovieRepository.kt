package com.verindrarizya.core.domain.repository

import com.verindrarizya.core.data.Resource
import com.verindrarizya.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getMovies(): Flow<Resource<List<Movie>>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun setFavoriteMovie(movie: Movie, state: Boolean)

}