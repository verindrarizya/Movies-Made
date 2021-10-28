package com.verindrarizya.core.util

import com.verindrarizya.core.data.source.local.entity.MovieEntity

object LocalDataDummy {

    val listMovieEntity: MutableList<MovieEntity> = mutableListOf(
        MovieEntity(
            1,
            "Movie1 title",
            "Movie1 poster",
            "Movie1 date",
            9.00,
            "Movie1 overview",
            false
        ),
        MovieEntity(
            2,
            "Movie2 title",
            "Movie2 poster",
            "Movie2 date",
            9.50,
            "Movie2 overview",
            false
        ),
        MovieEntity(
            3,
            "Movie3 title",
            "Movie3 poster",
            "Movie3 date",
            8.00,
            "Movie3 overview",
            true
        )
    )

    val newMovieEntity = MovieEntity(
        4,
        "Movie4 title",
        "Movie4 poster",
        "Movie4 date",
        8.50,
        "Movie4 overview",
        false
    )

    val newMovies = listOf(newMovieEntity)

}