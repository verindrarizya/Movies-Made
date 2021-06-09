package com.verindrarizya.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.verindrarizya.core.domain.model.Movie
import com.verindrarizya.core.ui.MovieAdapter
import com.verindrarizya.core.utils.setGone
import com.verindrarizya.core.utils.setVisible
import com.verindrarizya.favorite.di.favoriteModule
import com.verindrarizya.movies.R
import com.verindrarizya.movies.databinding.ActivityMovieBinding
import com.verindrarizya.movies.detailmovie.MovieDetailActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class MovieFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding
    private val movieFavoriteViewModel: MovieFavoriteViewModel by viewModel()
    private val dividerItemDecoration: DividerItemDecoration by inject()
    private val linearLayoutManager: LinearLayoutManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

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
            if (it.isEmpty()) binding.tvEmptyData.setVisible() else binding.tvEmptyData.setGone()
        }
    }

    private fun initAdapter(data: List<Movie>) {
        val movieAdapter = MovieAdapter()

        movieAdapter.setData(data)
        movieAdapter.onItemClick = {
            val intent = Intent(this@MovieFavoriteActivity, MovieDetailActivity::class.java)
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, it)
            startActivity(intent)
        }

        with(binding.rvMovies) {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            addItemDecoration(dividerItemDecoration)
            adapter = movieAdapter
        }
    }
}