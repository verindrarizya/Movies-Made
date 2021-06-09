package com.verindrarizya.movies

import android.app.Application
import com.verindrarizya.core.di.databaseModule
import com.verindrarizya.core.di.networkModule
import com.verindrarizya.core.di.repositoryModule
import com.verindrarizya.movies.di.useCaseModule
import com.verindrarizya.movies.di.viewModelModule
import com.verindrarizya.movies.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(listOf(
                databaseModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
                viewModule
            ))
        }
    }
}