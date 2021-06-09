package com.verindrarizya.core.domain.usecase

import com.verindrarizya.core.data.Resource
import com.verindrarizya.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getMovies(): Flow<Resource<List<Movie>>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun setFavoriteTourism(movie: Movie, state: Boolean)

}