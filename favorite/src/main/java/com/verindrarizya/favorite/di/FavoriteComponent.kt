package com.verindrarizya.favorite.di

import android.content.Context
import com.verindrarizya.movies.di.FavoriteModuleDependencies
import com.verindrarizya.favorite.ui.MovieFavoriteActivity
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }

    fun inject(movieFavoriteActivity: MovieFavoriteActivity)

}