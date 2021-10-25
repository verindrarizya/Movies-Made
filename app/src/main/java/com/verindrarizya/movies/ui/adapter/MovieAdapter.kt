package com.verindrarizya.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.verindrarizya.core.data.source.remote.network.ApiAssets
import com.verindrarizya.core.domain.model.Movie
import com.verindrarizya.movies.R
import com.verindrarizya.movies.databinding.ItemMovieBinding

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val listMovie = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    inner class MovieViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listMovie[adapterPosition])
            }
        }

        fun bind(data: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load("${ApiAssets.BASE_IMAGE_URL}${data.poster}")
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_connection_off_24)
                    .into(imgPoster)

                tvTitle.text = data.title
                tvRating.text = data.rating.toString()
                tvDate.text = data.date
            }
        }
    }

    fun setData(data: List<Movie>?) {
        if (data == null) return
        listMovie.clear()
        listMovie.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    override fun getItemCount(): Int = listMovie.size
}