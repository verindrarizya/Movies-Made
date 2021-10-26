package com.verindrarizya.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.verindrarizya.core.domain.usecase.MovieUseCase
import com.verindrarizya.movies.ui.detailmovie.MovieDetailViewModel
import com.verindrarizya.movies.ui.listmovie.MovieViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val movieUseCase: MovieUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            MovieDetailViewModel(movieUseCase) as T
        } else if(modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            MovieViewModel(movieUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Class Not Found")
        }
    }
}