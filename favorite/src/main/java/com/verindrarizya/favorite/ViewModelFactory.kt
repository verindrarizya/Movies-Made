package com.verindrarizya.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.verindrarizya.core.domain.usecase.MovieUseCase
import com.verindrarizya.favorite.ui.MovieFavoriteViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val movieUseCase: MovieUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieFavoriteViewModel::class.java)) {
            return MovieFavoriteViewModel(movieUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Class Not Founf")
        }
    }
}