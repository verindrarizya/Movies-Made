package com.verindrarizya.favorite.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.verindrarizya.core.domain.model.Movie
import com.verindrarizya.movies.ui.adapter.MovieAdapter
import com.verindrarizya.core.utils.setGone
import com.verindrarizya.core.utils.setVisible
import com.verindrarizya.favorite.ViewModelFactory
import com.verindrarizya.favorite.di.DaggerFavoriteComponent
import com.verindrarizya.movies.MyApplication
import com.verindrarizya.movies.R
import com.verindrarizya.movies.databinding.ActivityMovieBinding
import com.verindrarizya.movies.di.FavoriteModuleDependencies
import com.verindrarizya.movies.ui.detailmovie.MovieDetailActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class MovieFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val movieFavoriteViewModel: MovieFavoriteViewModel by viewModels { viewModelFactory }

    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // disabling swipe to refresh layout
        binding.swipeRefreshLayout.isEnabled = false

        initActionBar()
        initFavMoviesObserver()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initActionBar() {
        supportActionBar?.apply {
            title = getString(R.string.favorite_movies)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initFavMoviesObserver() {
        movieFavoriteViewModel.favoriteMovies.observe(this) {
            initAdapter(it)
            if (it.isEmpty()) binding.viewEmptyContainer.setVisible() else binding.viewEmptyContainer.setGone()
        }
    }

    private fun initAdapter(data: List<Movie>) {
        val movieAdapter = MovieAdapter()

        movieAdapter.setData(data)
        movieAdapter.onItemClick = { movie: Movie ->
            val intent = Intent(this@MovieFavoriteActivity, MovieDetailActivity::class.java)
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movie.id)
            startActivity(intent)
        }

        with(binding.rvMovies) {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }
}