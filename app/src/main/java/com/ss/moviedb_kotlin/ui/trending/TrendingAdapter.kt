package com.ss.moviedb_kotlin.ui.trending

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ss.moviedb_kotlin.databinding.ItemMovieBackdropBinding
import com.ss.moviedb_kotlin.model.movies.TrendingMovie
import com.ss.moviedb_kotlin.ui.trending.TrendingAdapter.TrendingViewHolder
import com.ss.moviedb_kotlin.util.Const

class TrendingAdapter : PagingDataAdapter<TrendingMovie, TrendingViewHolder>(DifferCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder(
            ItemMovieBackdropBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        val movie: TrendingMovie? = getItem(position)

        if (movie != null) {
            holder.bind(movie)
        }
    }

    companion object DifferCallback : DiffUtil.ItemCallback<TrendingMovie>() {
        override fun areItemsTheSame(oldItem: TrendingMovie, newItem: TrendingMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TrendingMovie,
            newItem: TrendingMovie
        ): Boolean {
            return oldItem == newItem
        }
    }

    class TrendingViewHolder(private var binding: ItemMovieBackdropBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: TrendingMovie) {
            // * Title placeholder
            binding.textMovieBackdropTitle.text = movie.title

            if (!movie.posterPath.isNullOrBlank()) {
                Glide.with(binding.imageMovieBackdrop)
                    .load(Const.IMG_URL_780 + movie.backdropPath)
                    .into(binding.imageMovieBackdrop)
            }
        }
    }
}