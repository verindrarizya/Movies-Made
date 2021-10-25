package com.verindrarizya.movies.di

import com.verindrarizya.core.domain.usecase.MovieInteractor
import com.verindrarizya.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun bindAppMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

}