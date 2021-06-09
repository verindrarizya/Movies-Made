package com.verindrarizya.favorite.di

import com.verindrarizya.favorite.MovieFavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { MovieFavoriteViewModel(get()) }
}