package com.verindrarizya.movies.di

import com.verindrarizya.core.domain.usecase.MovieInteractor
import com.verindrarizya.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindAppMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

}