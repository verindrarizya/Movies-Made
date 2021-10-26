package com.verindrarizya.core.domain.usecase

import com.verindrarizya.core.data.Resource
import com.verindrarizya.core.domain.model.Movie
import com.verindrarizya.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository): MovieUseCase {

    override fun getMovies(): Flow<Resource<List<Movie>>> = movieRepository.getMovies()

    override fun getMovie(id: Int): Flow<Movie> = movieRepository.getMovie(id)

    override fun getFavoriteMovies(): Flow<List<Movie>> = movieRepository.getFavoriteMovies()

    override suspend fun setFavoriteTourism(movie: Movie) = movieRepository.setFavoriteMovie(movie)
}