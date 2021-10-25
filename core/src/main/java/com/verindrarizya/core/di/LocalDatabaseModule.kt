package com.verindrarizya.core.di

import androidx.room.Room
import android.content.Context
import com.verindrarizya.core.data.source.local.room.MovieDao
import com.verindrarizya.core.data.source.local.room.MovieDatabase
import dagger.Module
import dagger.Provides
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
class LocalDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): MovieDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("VerindraMovies".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie.db"
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.movieDao()
}