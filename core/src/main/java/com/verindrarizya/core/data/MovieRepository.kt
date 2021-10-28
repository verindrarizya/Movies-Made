package com.verindrarizya.core.data

import com.verindrarizya.core.data.source.local.LocalDataSource
import com.verindrarizya.core.data.source.remote.RemoteDataSource
import com.verindrarizya.core.data.source.remote.network.ApiResponse
import com.verindrarizya.core.data.source.remote.response.MovieResponse
import com.verindrarizya.core.domain.model.Movie
import com.verindrarizya.core.domain.repository.IMovieRepository
import com.verindrarizya.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): IMovieRepository {

    override fun getMovies(): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getMovies().map {
                    DataMapper.mapEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Movie>?): Boolean = true
                // data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> = remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()
    }

    override fun getMovie(id: Int): Flow<Movie> =
        localDataSource.getMovie(id).map {
            DataMapper.mapEntityToDomain(it)
        }

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        localDataSource.getFavoriteMovies().map {
            DataMapper.mapEntitiesToDomain(it)
        }

    override suspend fun setFavoriteMovie(movie: Movie) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        localDataSource.setFavoriteMovie(movieEntity)
    }
}