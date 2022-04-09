package com.ss.moviedb_kotlin.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ss.moviedb_kotlin.databinding.ItemMoviePosterBinding
import com.ss.moviedb_kotlin.model.movies.NowPlayingMovie
import com.ss.moviedb_kotlin.ui.NowPlayingAdapter.NowPlayingViewHolder
import com.ss.moviedb_kotlin.util.Const

class NowPlayingAdapter :
    PagingDataAdapter<NowPlayingMovie, NowPlayingViewHolder>(DifferCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        return NowPlayingViewHolder(ItemMoviePosterBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        val movie: NowPlayingMovie? = getItem(position)

        if (movie != null) {
            holder.bind(movie)
        }
    }

    companion object DifferCallback : DiffUtil.ItemCallback<NowPlayingMovie>() {
        override fun areItemsTheSame(oldItem: NowPlayingMovie, newItem: NowPlayingMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NowPlayingMovie,
            newItem: NowPlayingMovie
        ): Boolean {
            return oldItem == newItem
        }
    }

    class NowPlayingViewHolder(private var binding: ItemMoviePosterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: NowPlayingMovie) {
            binding.textTitle.text = movie.title
            Glide.with(binding.imagePoster)
                .load(Const.IMG_URL_500 + movie.posterPath)
                .into(binding.imagePoster)
        }
    }
}