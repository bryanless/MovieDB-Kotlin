package com.ss.moviedb_kotlin.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviedb_kotlin.databinding.ItemMovieGenreBinding
import com.ss.moviedb_kotlin.model.movies.GenreMovie
import com.ss.moviedb_kotlin.ui.detail.DetailGenreAdapter.GenreViewHolder

class DetailGenreAdapter : RecyclerView.Adapter<GenreViewHolder>() {
    private var genres: List<GenreMovie> = listOf()
    fun setGenres(genres: List<GenreMovie>) {
        this.genres = genres
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            ItemMovieGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre: GenreMovie = genres[position]

        holder.bind(genre)
    }

    override fun getItemCount(): Int = genres.size

    class GenreViewHolder(private val binding: ItemMovieGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: GenreMovie) {
            binding.textMovieGenreItem.text = genre.name
        }
    }

}