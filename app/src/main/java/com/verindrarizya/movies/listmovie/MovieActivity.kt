package com.verindrarizya.movies.listmovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.verindrarizya.core.data.Resource
import com.verindrarizya.core.domain.model.Movie
import com.verindrarizya.core.ui.MovieAdapter
import com.verindrarizya.core.utils.setGone
import com.verindrarizya.core.utils.setVisible
import com.verindrarizya.movies.R
import com.verindrarizya.movies.databinding.ActivityMovieBinding
import com.verindrarizya.movies.detailmovie.MovieDetailActivity
import com.verindrarizya.movies.detailmovie.MovieDetailActivity.Companion.EXTRA_MOVIE
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MovieActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMovieBinding
    private val movieViewModel: MovieViewModel by viewModel()
    private val dividerItemDecoration: DividerItemDecoration by inject()
    private val linearLayoutManager: LinearLayoutManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        initMoviesObserver()
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

    private fun moveToFavoriteActivity() {
        val intent = Intent(this, Class.forName("com.verindrarizya.favorite.MovieFavoriteActivity"))
        startActivity(intent)
    }

    private fun initActionBar() {
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun initMoviesObserver() {
        movieViewModel.movies.observe(this) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> binding.progressBar.setVisible()

                    is Resource.Succcess -> {
                        binding.progressBar.setGone()
                        movies.data?.let { initAdapter(it) }
                    }

                    is Resource.Error -> {
                        binding.progressBar.setGone()
                        binding.tvEmptyData.setVisible()
                        binding.tvEmptyData.text = getString(R.string.error_statement)
                    }
                }
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
            addItemDecoration(dividerItemDecoration)
            adapter = movieAdapter
        }
    }
}