package com.verindrarizya.movies.listmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.verindrarizya.core.domain.usecase.MovieUseCase

class MovieViewModel(movieUseCase: MovieUseCase): ViewModel() {

    val movies = movieUseCase.getMovies().asLiveData()

}