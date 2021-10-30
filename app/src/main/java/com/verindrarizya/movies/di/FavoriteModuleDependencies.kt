package com.verindrarizya.movies.di

import com.verindrarizya.core.domain.usecase.MovieInteractor
import com.verindrarizya.core.domain.usecase.MovieUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun movieInteractor(): MovieUseCase

}