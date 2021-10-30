package com.verindrarizya.movies.ui.listmovie

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.verindrarizya.core.domain.model.Movie
import com.verindrarizya.core.utils.setGone
import com.verindrarizya.core.utils.setVisible
import com.verindrarizya.movies.R
import com.verindrarizya.movies.databinding.ActivityMovieBinding
import com.verindrarizya.movies.ui.adapter.MovieAdapter
import com.verindrarizya.movies.ui.detailmovie.MovieDetailActivity
import com.verindrarizya.movies.ui.detailmovie.MovieDetailActivity.Companion.EXTRA_MOVIE_ID
import com.verindrarizya.movies.ui.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding

    private val movieViewModel: MovieViewModel by viewModels()

    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        initSwipeRefreshLayout()
        initMoviesObserver()
        initLoadingObserver()
        initErrorObserver()
    }

    private fun initSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            movieViewModel.getMovies()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    /**
     * Start of functions for appbar
     */
    private fun initActionBar() {
        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_favorite -> moveToFavoriteActivity()
            R.id.action_settings -> moveToSettingActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * End of functions for app bar
     */

    private fun initMoviesObserver() {
        movieViewModel.movies.observe(this) { movies ->
            if(movies != null) {
                binding.rvMovies.setVisible()
                initAdapter(movies)
            } else {
                binding.rvMovies.setGone()
                binding.viewEmptyContainer.setVisible()
            }
        }
    }

    private fun initAdapter(data: List<Movie>) {
        val movieAdapter = MovieAdapter()

        movieAdapter.setData(data)
        movieAdapter.onItemClick = { movie: Movie ->
            val intent = Intent(this@MovieActivity, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, movie.id)
            startActivity(intent)
        }

        with(binding.rvMovies) {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun initLoadingObserver() {
        movieViewModel.isLoading.observe(this) {
            if (it) {
                binding.rvMovies.setGone()
                binding.progressBar.setVisible()
            } else {
                binding.rvMovies.setVisible()
                binding.progressBar.setGone()
            }
        }
    }

    private fun initErrorObserver() {
        movieViewModel.isError.observe(this) {
            if (it) {
                binding.rvMovies.setGone()
                binding.viewEmptyContainer.setGone()
                binding.viewErrorContainer.setVisible()
            } else {
                binding.viewErrorContainer.setGone()
            }
        }
    }

    private fun moveToFavoriteActivity() {
        val intent = Intent(this, Class.forName("com.verindrarizya.favorite.ui.MovieFavoriteActivity"))
        startActivity(intent)
    }

    private fun moveToSettingActivity() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }
}