package com.verindrarizya.core.di

import androidx.room.Room
import com.verindrarizya.core.data.MovieRepository
import com.verindrarizya.core.data.source.local.LocalDataSource
import com.verindrarizya.core.data.source.local.room.MovieDatabase
import com.verindrarizya.core.data.source.remote.RemoteDataSource
import com.verindrarizya.core.data.source.remote.network.ApiAssets.BASE_URL
import com.verindrarizya.core.data.source.remote.network.ApiService
import com.verindrarizya.core.domain.repository.IMovieRepository
import com.verindrarizya.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {

    factory {
        get<MovieDatabase>().movieDao()
    }

    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("VerindraMovies".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(androidContext(), MovieDatabase::class.java, "movie.db")
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {

    single {
        val hostname = "developers.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/kBf1bI1z28Ytbj7xOYuejUOmxHRpY24T7W8mscSQ+tI=")
            .add(hostname, "sha256/jQJTbIh0grw0/1TkHSumWb+Fs0Ggogr621gT3PvPKG0=")
            .add(hostname, "sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {

    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }

    single<IMovieRepository> {
        MovieRepository(get(), get(), get())
    }
}