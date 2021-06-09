package com.verindrarizya.core.data.source.local.room

import androidx.room.*
import com.verindrarizya.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE is_favorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<MovieEntity>)

    @Update
    fun updateFavoriteMovie(movie: MovieEntity)

}