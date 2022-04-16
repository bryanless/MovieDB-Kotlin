package com.ss.moviedb_kotlin.ui.popular

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ss.moviedb_kotlin.databinding.ItemMoviePosterBinding
import com.ss.moviedb_kotlin.model.movies.PopularMovie
import com.ss.moviedb_kotlin.ui.home.HomeFragment
import com.ss.moviedb_kotlin.util.Const
import com.ss.moviedb_kotlin.ui.popular.PopularAdapter.PopularViewHolder

class PopularAdapter : PagingDataAdapter<PopularMovie, PopularViewHolder>(DifferCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(ItemMoviePosterBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val movie: PopularMovie? = getItem(position)

        if (movie != null) {
            holder.bind(movie)
        }
    }

    companion object DifferCallback : DiffUtil.ItemCallback<PopularMovie>() {
        override fun areItemsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PopularMovie,
            newItem: PopularMovie
        ): Boolean {
            return oldItem == newItem
        }
    }

    class PopularViewHolder(private var binding: ItemMoviePosterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: PopularMovie) {
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