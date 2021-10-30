package com.verindrarizya.core.di

import com.verindrarizya.core.data.MovieRepository
import com.verindrarizya.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(movieRepository: MovieRepository): IMovieRepository
}