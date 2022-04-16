package com.ss.moviedb_kotlin.ui.popular

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
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
import com.ss.moviedb_kotlin.ui.home.HomeFragmentDirections
import com.ss.moviedb_kotlin.ui.popular.PopularAdapter.PopularViewHolder
import com.ss.moviedb_kotlin.util.Const

class PopularAdapter : PagingDataAdapter<PopularMovie, PopularViewHolder>(DifferCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(
            ItemMoviePosterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val movie: PopularMovie? = getItem(position)

        if (movie != null) {
            holder.bind(movie)
        }

        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie!!.id)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return if (super.getItemCount() < 10) super.getItemCount() else 10
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

    class PopularViewHolder(private val binding: ItemMoviePosterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: PopularMovie) {
            // * Title placeholder
            binding.textMoviePosterTitleItem.text = movie.title
            binding.textMoviePosterTitleItem.visibility = View.VISIBLE

            if (!movie.posterPath.isNullOrBlank()) {
                Glide.with(binding.imageMoviePosterItem)
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
                            binding.textMoviePosterTitleItem.visibility = View.INVISIBLE
                            return false
                        }
                    })
                    .into(binding.imageMoviePosterItem)
            }
        }
    }
}