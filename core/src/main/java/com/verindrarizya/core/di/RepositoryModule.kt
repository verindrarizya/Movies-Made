package com.verindrarizya.core.di

import com.verindrarizya.core.data.MovieRepository
import com.verindrarizya.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module

@Module(includes = [LocalDatabaseModule::class, RemoteModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(movieRepository: MovieRepository): IMovieRepository
}