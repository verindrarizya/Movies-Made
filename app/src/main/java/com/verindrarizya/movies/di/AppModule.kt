package com.verindrarizya.movies.di

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.verindrarizya.core.domain.usecase.MovieInteractor
import com.verindrarizya.core.domain.usecase.MovieUseCase
import com.verindrarizya.movies.detailmovie.MovieDetailViewModel
import com.verindrarizya.movies.listmovie.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> {
        MovieInteractor(get())
    }
}

val viewModelModule = module {

    viewModel { MovieViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}

val viewModule = module {
    factory {
        DividerItemDecoration(get(), DividerItemDecoration.VERTICAL)
    }

    factory {
        LinearLayoutManager(get())
    }
}