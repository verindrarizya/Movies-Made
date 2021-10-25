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
import com.verindrarizya.movies.MyApplication
import com.verindrarizya.movies.R
import com.verindrarizya.movies.ViewModelFactory
import com.verindrarizya.movies.databinding.ActivityMovieBinding
import com.verindrarizya.movies.ui.adapter.MovieAdapter
import com.verindrarizya.movies.ui.detailmovie.MovieDetailActivity
import com.verindrarizya.movies.ui.detailmovie.MovieDetailActivity.Companion.EXTRA_MOVIE
import javax.inject.Inject

class MovieActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMovieBinding

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val movieViewModel: MovieViewModel by viewModels { viewModelFactory }

    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        initMoviesObserver()
        initLoadingObserver()
        initErrorObserver()
    }

    /**
     * Start of functions for appbar
     */
    private fun initActionBar() {
        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
           moveToFavoriteActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * End of functions for app bar
     */

    private fun initMoviesObserver() {
        movieViewModel.movies.observe(this) { movies ->
            if(movies != null) {
                initAdapter(movies)
            } else {
                binding.tvEmptyData.setVisible()
            }
        }
    }

    private fun initAdapter(data: List<Movie>) {
        val movieAdapter = MovieAdapter()

        movieAdapter.setData(data)
        movieAdapter.onItemClick = {
            val intent = Intent(this@MovieActivity, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, it)
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
                binding.progressBar.setVisible()
            } else {
                binding.progressBar.setGone()
            }
        }
    }

    private fun initErrorObserver() {
        movieViewModel.isError.observe(this) {
            if (it) {
                binding.tvEmptyData.setVisible()
            } else {
                binding.tvEmptyData.setGone()
            }
        }
    }

    private fun moveToFavoriteActivity() {
        val intent = Intent(this, Class.forName("com.verindrarizya.favorite.ui.MovieFavoriteActivity"))
        startActivity(intent)
    }
}