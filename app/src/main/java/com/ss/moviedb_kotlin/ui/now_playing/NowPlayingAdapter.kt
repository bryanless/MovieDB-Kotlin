package com.ss.moviedb_kotlin.ui.now_playing

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ss.moviedb_kotlin.databinding.ItemMoviePosterBinding
import com.ss.moviedb_kotlin.model.movies.NowPlayingMovie
import com.ss.moviedb_kotlin.ui.now_playing.NowPlayingAdapter.NowPlayingViewHolder
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
            // * Title placeholder
            binding.textMoviePosterTitle.text = movie.title
            binding.textMoviePosterTitle.visibility = View.VISIBLE

            if (!movie.posterPath.isNullOrBlank()) {
                Glide.with(binding.imageMoviePoster)
                    .load(Const.IMG_URL_500 + movie.posterPath)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            // * Hide title placeholder when image is loaded
                            binding.textMoviePosterTitle.visibility = View.INVISIBLE
                            return false
                        }
                    })
                    .into(binding.imageMoviePoster)
            }
        }
    }
}