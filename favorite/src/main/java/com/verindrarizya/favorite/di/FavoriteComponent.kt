package com.verindrarizya.favorite.di

import com.verindrarizya.core.di.CoreComponent
import com.verindrarizya.favorite.ui.MovieFavoriteActivity
import dagger.Component

@FavoriteScope
@Component(dependencies = [CoreComponent::class], modules = [FavoriteModule::class])
interface FavoriteComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): FavoriteComponent
    }

    fun inject(movieFavoriteActivity: MovieFavoriteActivity)

}