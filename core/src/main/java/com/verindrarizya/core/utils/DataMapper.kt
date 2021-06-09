package com.verindrarizya.core.utils

import com.verindrarizya.core.data.source.local.entity.MovieEntity
import com.verindrarizya.core.data.source.remote.response.MovieResponse
import com.verindrarizya.core.domain.model.Movie

object DataMapper {

    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()

        input.forEach {
            val movieEntity = MovieEntity(
                it.id,
                it.title,
                it.posterPath,
                it.releaseDate,
                it.voteAverage,
                it.overview,
                isFavorite = false
            )
            movieList.add(movieEntity)
        }

        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> {
        val movieList = ArrayList<Movie>()

        input.forEach {
            val movie = Movie(
                it.id,
                it.title,
                it.poster,
                it.date,
                it.rating,
                it.overview,
                it.isFavorite
            )
            movieList.add(movie)
        }

        return movieList
    }

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        input.id,
        input.title,
        input.poster,
        input.date,
        input.rating,
        input.overview,
        input.isFavorite
    )

}