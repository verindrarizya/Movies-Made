package com.verindrarizya.movies.detailmovie

import androidx.lifecycle.ViewModel
import com.verindrarizya.core.domain.model.Movie
import com.verindrarizya.core.domain.usecase.MovieUseCase

class MovieDetailViewModel(private val movieUseCase: MovieUseCase): ViewModel() {
    fun setFavoriteMovie(movie: Movie, newState: Boolean) = movieUseCase.setFavoriteTourism(movie, newState)
}