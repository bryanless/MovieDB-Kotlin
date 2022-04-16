package com.ss.moviedb_kotlin.ui.top_rated

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
import com.ss.moviedb_kotlin.databinding.ItemMovieBackdropBinding
import com.ss.moviedb_kotlin.databinding.ItemMoviePosterBinding
import com.ss.moviedb_kotlin.model.movies.TopRatedMovie
import com.ss.moviedb_kotlin.util.Const

class TopRatedAdapter : PagingDataAdapter<TopRatedMovie, TopRatedAdapter.TopRatedViewHolder>(DifferCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedViewHolder {
        return TopRatedViewHolder(ItemMovieBackdropBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) {
        val movie: TopRatedMovie? = getItem(position)

        if (movie != null) {
            holder.bind(movie)
        }
    }

    companion object DifferCallback : DiffUtil.ItemCallback<TopRatedMovie>() {
        override fun areItemsTheSame(oldItem: TopRatedMovie, newItem: TopRatedMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TopRatedMovie,
            newItem: TopRatedMovie
        ): Boolean {
            return oldItem == newItem
        }
    }

    class TopRatedViewHolder(private var binding: ItemMovieBackdropBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: TopRatedMovie) {
            // * Title placeholder
            binding.textMovieBackdropTitle.text = movie.title
            binding.textMovieBackdropTitle.visibility = View.VISIBLE

            if (!movie.posterPath.isNullOrBlank()) {
                Glide.with(binding.imageMovieBackdrop)
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
                            binding.textMovieBackdropTitle.visibility = View.INVISIBLE
                            return false
                        }
                    })
                    .into(binding.imageMovieBackdrop)
            }
        }
    }
}