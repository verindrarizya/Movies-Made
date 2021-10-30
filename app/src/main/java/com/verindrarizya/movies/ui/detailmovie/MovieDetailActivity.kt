package com.verindrarizya.movies.ui.detailmovie

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.verindrarizya.core.data.source.remote.network.ApiAssets.BASE_IMAGE_URL
import com.verindrarizya.core.domain.model.Movie
import com.verindrarizya.core.utils.setGone
import com.verindrarizya.core.utils.setVisible
import com.verindrarizya.movies.R
import com.verindrarizya.movies.databinding.ActivityMovieDetailBinding
import com.verindrarizya.movies.databinding.ContentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var contentMovieDetailBinding: ContentMovieDetailBinding

    private val movieDetailViewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        contentMovieDetailBinding = binding.contentMovieDetail
        setContentView(binding.root)

        initActionBar()
        binding.progressBar.setVisible()

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        movieDetailViewModel.setMovieId(movieId)

        initProgressBarObserver()
        initMovieObserver()
        initFabStateObserver()
        initFabOnClick()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initMovieObserver() {
        movieDetailViewModel.movie.observe(this) {
            populateContentMovieDetail(it)
        }
    }

    private fun initFabStateObserver() {
        movieDetailViewModel.isFavorite.observe(this) {
            setFabMovie(it)
        }
    }

    private fun initProgressBarObserver() {
        movieDetailViewModel.isLoading.observe(this) { isLoading: Boolean ->
            if(isLoading) {
                binding.progressBar.setVisible()
            } else {
                binding.progressBar.setGone()
                binding.content.setVisible()
            }
        }
    }

    private fun initFabOnClick() {
        binding.fabFavorite.setOnClickListener { movieDetailViewModel.setFavoriteMovie() }
    }

    private fun populateContentMovieDetail(data: Movie) {
        with(contentMovieDetailBinding) {
            Glide.with(this@MovieDetailActivity)
                .load("$BASE_IMAGE_URL${data.poster}")
                .transition(withCrossFade())
                .error(R.drawable.ic_connection_off_24)
                .into(imgPoster)

            tvTitle.text = data.title
            tvReleaseDate.text = data.date
            tvRating.text = data.rating.toString()
            tvOverview.text = data.overview
        }
    }

    private fun initActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.movie_detail)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setFabMovie(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_filled_24)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_outline_24)
        }
    }
}