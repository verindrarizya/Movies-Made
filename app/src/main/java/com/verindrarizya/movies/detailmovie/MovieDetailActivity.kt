package com.verindrarizya.movies.detailmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.verindrarizya.core.data.source.remote.network.ApiAssets.BASE_IMAGE_URL
import com.verindrarizya.core.domain.model.Movie
import com.verindrarizya.core.utils.setGone
import com.verindrarizya.core.utils.setVisible
import com.verindrarizya.movies.MyApplication
import com.verindrarizya.movies.R
import com.verindrarizya.movies.ViewModelFactory
import com.verindrarizya.movies.databinding.ActivityMovieDetailBinding
import com.verindrarizya.movies.databinding.ContentMovieDetailBinding
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var contentMovieDetailBinding: ContentMovieDetailBinding

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val movieDetailViewModel: MovieDetailViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        contentMovieDetailBinding = binding.contentMovieDetail
        setContentView(binding.root)

        initActionBar()
        binding.progressBar.setVisible()

        val movieDetail = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        Log.d("MovieDetailTag", movieDetail.toString())
        if (movieDetail != null) {
            binding.progressBar.setGone()
            binding.content.setVisible()
            populateContentMovieDetail(movieDetail)

            setFabMovie(movieDetail.isFavorite)
            binding.fabFavorite.setOnClickListener {
                val newState = !movieDetail.isFavorite
                movieDetailViewModel.setFavoriteMovie(movieDetail, newState)
                setFabMovie(newState)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun populateContentMovieDetail(data: Movie) {
        with(contentMovieDetailBinding) {
            Glide.with(this@MovieDetailActivity)
                .load("$BASE_IMAGE_URL${data.poster}")
                .apply(RequestOptions().override(120, 170))
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