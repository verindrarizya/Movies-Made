package com.verindrarizya.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.verindrarizya.core.domain.usecase.MovieUseCase

class MovieFavoriteViewModel(movieUseCase: MovieUseCase): ViewModel() {
    val favoriteMovies = movieUseCase.getFavoriteMovies().asLiveData()
}