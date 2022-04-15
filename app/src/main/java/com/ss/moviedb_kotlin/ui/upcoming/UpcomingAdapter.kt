package com.ss.moviedb_kotlin.ui.upcoming

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.DifferCallback
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ss.moviedb_kotlin.databinding.ItemMoviePosterBinding
import com.ss.moviedb_kotlin.model.movies.UpcomingMovie
import com.ss.moviedb_kotlin.ui.upcoming.UpcomingAdapter.UpcomingViewHolder
import com.ss.moviedb_kotlin.util.Const

class UpcomingAdapter : PagingDataAdapter<UpcomingMovie, UpcomingViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        return UpcomingViewHolder(ItemMoviePosterBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val movie: UpcomingMovie? = getItem(position)

        if (movie != null) {
            holder.bind(movie)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<UpcomingMovie>() {
        override fun areItemsTheSame(oldItem: UpcomingMovie, newItem: UpcomingMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UpcomingMovie, newItem: UpcomingMovie): Boolean {
            return oldItem == newItem
        }

    }

    class UpcomingViewHolder(private var binding: ItemMoviePosterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: UpcomingMovie) {
            // * Title placeholder
            binding.textTitle.text = movie.title
            binding.textTitle.visibility = View.VISIBLE

            if (!movie.posterPath.isNullOrBlank()) {
                Glide.with(binding.imagePoster)
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
                            binding.textTitle.visibility = View.INVISIBLE
                            return false
                        }
                    })
                    .into(binding.imagePoster)
            }
        }
    }
}