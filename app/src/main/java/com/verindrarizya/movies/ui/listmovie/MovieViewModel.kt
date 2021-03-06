package com.verindrarizya.movies.ui.listmovie

import androidx.lifecycle.*
import com.verindrarizya.core.data.Resource
import com.verindrarizya.core.domain.model.Movie
import com.verindrarizya.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean>
        get() = _isError

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            movieUseCase.getMovies().collect { resource ->

                when(resource) {
                    is Resource.Loading -> {
                        _isError.value = false
                        _isLoading.value = true
                    }

                    is Resource.Succcess -> {
                        _isLoading.value = false
                        _movies.value = resource.data
                    }

                    is Resource.Error -> {
                        _movies.value = null
                        _isLoading.value = false
                        _isError.value = true
                    }
                }
            }
        }
    }

}