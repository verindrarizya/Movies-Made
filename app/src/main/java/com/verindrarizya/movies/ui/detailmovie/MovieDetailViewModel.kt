package com.verindrarizya.movies.ui.detailmovie

import androidx.lifecycle.*
import com.verindrarizya.core.domain.model.Movie
import com.verindrarizya.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    private val _movieId = MutableLiveData<Int>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val movie: LiveData<Movie> = _movieId.switchMap {
        _isLoading.value = false
        movieUseCase.getMovie(it).asLiveData()
    }

    val isFavorite: LiveData<Boolean> = movie.map {
        it.isFavorite
    }

    init {
        _isLoading.value = true
    }

    fun setMovieId(id: Int) {
        _movieId.value = id
    }

    fun setFavoriteMovie()  {
        viewModelScope.launch {
            movie.value?.let { movieUseCase.setFavoriteTourism(it) }
        }
    }
}