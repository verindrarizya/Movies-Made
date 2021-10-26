package com.verindrarizya.core.domain.usecase

import com.verindrarizya.core.data.Resource
import com.verindrarizya.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getMovies(): Flow<Resource<List<Movie>>>

    fun getMovie(id: Int): Flow<Movie>

    fun getFavoriteMovies(): Flow<List<Movie>>

    suspend fun setFavoriteTourism(movie: Movie)

}