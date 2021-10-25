package com.verindrarizya.favorite.di

import com.verindrarizya.core.domain.usecase.MovieInteractor
import com.verindrarizya.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class FavoriteModule {

    @Binds
    abstract fun bindMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

}