package com.ss.moviedb_kotlin.ui.detail

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
import com.ss.moviedb_kotlin.model.movies.RecommendationMovie
import com.ss.moviedb_kotlin.ui.detail.RecommendationAdapter.RecommendationViewHolder
import com.ss.moviedb_kotlin.ui.detail.DetailFragmentDirections
import com.ss.moviedb_kotlin.util.Const

class RecommendationAdapter :
    PagingDataAdapter<RecommendationMovie, RecommendationViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        return RecommendationViewHolder(
            ItemMoviePosterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val movie: RecommendationMovie? = getItem(position)

        if (movie != null) {
            holder.bind(movie)
        }

        holder.itemView.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentSelf(movie!!.id)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return if (super.getItemCount() < 10) super.getItemCount() else 10
    }

    companion object DiffCallback : DiffUtil.ItemCallback<RecommendationMovie>() {
        override fun areItemsTheSame(
            oldItem: RecommendationMovie,
            newItem: RecommendationMovie
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RecommendationMovie,
            newItem: RecommendationMovie
        ): Boolean {
            return oldItem == newItem
        }
    }

    class RecommendationViewHolder(private val binding: ItemMoviePosterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: RecommendationMovie) {
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