package com.verindrarizya.core.util

import com.verindrarizya.core.data.source.remote.response.ListResponses
import com.verindrarizya.core.data.source.remote.response.MovieResponse

object RemoteDataDummy {

    val listResponses = ListResponses(listOf(
        MovieResponse(
            "movie1 overview",
            "movie1 releaseDate",
            199.0,
            1,
            "movie1 title",
            "movie1 posterPath"
        ),
        MovieResponse(
            "movie2 overview",
            "movie2 releaseDate",
            299.0,
            2,
            "movie2 title",
            "movie2 posterPath"
        )
    ))

    val emptyListResponse = ListResponses(listOf())

}