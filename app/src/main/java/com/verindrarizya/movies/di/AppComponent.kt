package com.verindrarizya.movies.di

import com.verindrarizya.core.di.CoreComponent
import com.verindrarizya.movies.detailmovie.MovieDetailActivity
import com.verindrarizya.movies.listmovie.MovieActivity
import dagger.Component

@AppScope
@Component(dependencies = [CoreComponent::class], modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(movieDetailActivity: MovieDetailActivity)
    fun inject(movieActivity: MovieActivity)

}