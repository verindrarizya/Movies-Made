package com.verindrarizya.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.verindrarizya.core.data.source.remote.network.ApiAssets.BASE_IMAGE_URL
import com.verindrarizya.core.databinding.ItemMovieRowBinding
import com.verindrarizya.core.domain.model.Movie

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val listMovie = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    inner class MovieViewHolder(private val binding: ItemMovieRowBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listMovie[adapterPosition])
            }
        }

        fun bind(data: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load("$BASE_IMAGE_URL${data.poster}")
                    .apply(RequestOptions().override(100, 150))
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
        val itemMovieRowBinding = ItemMovieRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemMovieRowBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    override fun getItemCount(): Int = listMovie.size
}