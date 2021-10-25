package com.verindrarizya.movies

import android.app.Application
import com.verindrarizya.core.di.CoreComponent
import com.verindrarizya.core.di.DaggerCoreComponent
import com.verindrarizya.movies.di.AppComponent
import com.verindrarizya.movies.di.DaggerAppComponent

class MyApplication: Application() {
    val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(this)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}