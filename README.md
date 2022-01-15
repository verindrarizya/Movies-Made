# Movies-Made

[![verindrarizya](https://circleci.com/gh/verindrarizya/Movies-Made.svg?style=svg)](https://circleci.com/gh/verindrarizya/Movies-Made)

## How to use
Get your own API KEY from [TMDB](https://www.themoviedb.org/), and put it in buildConfigField inside build.gradle in core module

## About
A simple application applying clean architecture and modularization for fetching and showing Movies from [TMDB](https://www.themoviedb.org/).

## Features
- Fetch and show movies
- Favorite Movies
- Add Favorite Movie
- Delete Favorite Movie
- Setting, Reminder every 2 days

## Clean Architecture
<div align="center">
    <img src="assets/clean-architecture.png"/>
</div>

- Presentation Layer, contains UI that are coordinated by ViewModels which execute the use cases.
- Domain Layer, completely pure kotlin module containing entities, use cases, & repository interfaces.
- Data Layer, contains repository implementation with multiple data sources. Repository responsible to coordinate data from different data sources.

## Build With
- [Kotlin](https://kotlinlang.org/), a modern statically typed programming language.
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Flow](https://kotlinlang.org/docs/flow.html)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), an observable data holder.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), class for storing and manage UI-related data in a lifecycle conscious way.
- [Room](https://developer.android.com/training/data-storage/room), a persistence library that provides an abstraction layer over SQLite.
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager), an API that make it easy to schedule reliable, asynchronous tasks that are expected to run.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android),  a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project that's built on top of [Dagger](https://dagger.dev/)
- [Retrofit](https://square.github.io/retrofit/), a type-safe HTTP client for Android and Java
- [OkHttp](https://square.github.io/okhttp/), an HTTP & HTTP/2 client for Android and Java applications
- [Gson](https://github.com/google/gson), a Java serialization/deserialization library to convert Java Objects into JSON and vice versa.
- [Glide](https://bumptech.github.io/glide/), a fast and efficient image loading library for Android.
- [Shimmer](https://github.com/facebook/shimmer-android), an Android library that provides an easy way to add a shimmer effect
- [Lottie](https://lottiefiles.com/blog/working-with-lottie/getting-started-with-lottie-animations-in-android-app), is a mobile library for Android and iOS that parses Adobe After Effects animations exported as json with Bodymovin and renders them natively on mobile
- Unit Test
- Database encryption with [SQLCipher](https://github.com/sqlcipher/android-database-sqlcipher)
- Obfuscation
- Memory leak detection with [LeakCanary](https://square.github.io/leakcanary/)([See this branch](https://github.com/verindrarizya/Movies-Made/tree/with-leakcanary))
- Certificate Pinning
