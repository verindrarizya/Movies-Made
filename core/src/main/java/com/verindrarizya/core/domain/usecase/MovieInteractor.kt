package com.verindrarizya.core.domain.usecase

import com.verindrarizya.core.data.Resource
import com.verindrarizya.core.domain.model.Movie
import com.verindrarizya.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository): MovieUseCase {

    override fun getMovies(): Flow<Resource<List<Movie>>> = movieRepository.getMovies()

    override fun getFavoriteMovies(): Flow<List<Movie>> = movieRepository.getFavoriteMovies()

    override fun setFavoriteTourism(movie: Movie, state: Boolean) = movieRepository.setFavoriteMovie(movie, state)
}