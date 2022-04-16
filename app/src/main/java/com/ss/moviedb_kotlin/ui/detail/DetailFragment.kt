package com.ss.moviedb_kotlin.ui.detail

import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ss.moviedb_kotlin.R
import com.ss.moviedb_kotlin.data.repository.DetailRepository
import com.ss.moviedb_kotlin.databinding.DetailFragmentBinding
import com.ss.moviedb_kotlin.network.MovieDbApi
import com.ss.moviedb_kotlin.util.Const

class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            DetailRepository(MovieDbApi)
        )
    }

    private var movieId: Int = -1
    private var videoSite: String = ""
    private var videoKey: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setListener()
    }

    private fun setListener() {
        binding.buttonMovieWatchTrailerDetail.setOnClickListener {
            // ? YouTube
            if (TextUtils.equals(videoSite, Const.YOUTUBE)) {
                // * Set intent to launch YouTube
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Const.YOUTUBE_WATCH_URL + videoKey))

                // * Check if YouTube installed
                val manager = requireActivity().packageManager
                val info = manager.queryIntentActivities(intent, 0) as ArrayList<ResolveInfo>
                if (info.size > 0) {
                    // * YouTube installed
                    intent.setPackage("com.google.android.youtube")
                } else {
                    // * Youtube not installed
                }

                // * Launch YouTube
                startActivity(intent)
            } else {
                // * Make snackbar
                val snackbar = Snackbar.make(
                    requireView(),
                    getString(R.string.text_video_failed),
                    Snackbar.LENGTH_SHORT
                )
                snackbar.anchorView = requireActivity().findViewById(R.id.bottom_navigation_view)

                // * Set action
                snackbar.setAction(
                    getString(R.string.text_dismiss)
                ) { snackbar.dismiss() }
                snackbar.show()
            }
        }
    }

    private fun initView() {
        movieId = arguments?.getInt("movieId")!!

        viewModel.getDetailMovie(movieId)
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            val year = movie.releaseDate?.substring(0, 4)
            val runtime = movie.runtime
            val voteAverage = movie.voteAverage.toString()
            val voteCount = movie.voteCount.toString()
            val popularity = movie.popularity.toString()
            val productionCompanies = movie.productionCompanies?.map { it.name }?.joinToString(", ")
            val tagline = movie.tagline
            val overview = movie.overview

            val hour = runtime?.div(60).toString()
            val minute = runtime?.rem(60).toString()

            binding.textMoviePosterTitleDetail.text = movie.title
            binding.textMovieCaptionDetail.text =
                getString(R.string.text_movie_caption, year, hour, minute)
            binding.textMovieTitleDetail.text = movie.title
            binding.textMovieRatingDetail.text =
                getString(R.string.text_movie_rating, voteAverage, voteCount)
            binding.textMoviePopularityDetail.text = popularity
            binding.textMovieProductionCompaniesDetail.text = productionCompanies
            binding.textMovieDescriptionDetail.text =
                getString(R.string.text_movie_description, tagline, overview)
            if (!movie.backdropPath.isNullOrBlank()) {
                Glide.with(this)
                    .load(Const.IMG_URL_780 + movie.backdropPath)
                    .into(binding.imageMovieBackdropDetail)
            }
            if (!movie.posterPath.isNullOrBlank()) {
                Glide.with(this)
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
                            binding.textMoviePosterTitleDetail.visibility = View.INVISIBLE
                            return false
                        }
                    })
                    .into(binding.imageMoviePosterDetail)
            }

            val detailGenreAdapter = DetailGenreAdapter()
            movie.genres?.let { detailGenreAdapter.setGenres(it) }
            binding.recyclerViewMovieGenreDetail.apply {
                layoutManager =
                    FlexboxLayoutManager(requireContext(), FlexDirection.ROW, FlexWrap.WRAP)
                adapter = detailGenreAdapter
            }
        }

        viewModel.getVideosMovie(movieId)
        viewModel.videos.observe(viewLifecycleOwner) { videos ->
            if (videos.isNotEmpty()) {
                val officialVideo = videos.firstOrNull { video -> video.official == true }
                val youtubeVideo = videos.firstOrNull { video -> video.key == Const.YOUTUBE }

                when {
                    officialVideo != null -> {
                        videoSite = officialVideo.site.toString()
                        videoKey = officialVideo.key.toString()
                    }
                    youtubeVideo != null -> {
                        videoSite = youtubeVideo.site.toString()
                        videoKey = youtubeVideo.key.toString()
                    }
                    else -> {
                        videoSite = videos.first().site.toString()
                        videoKey = videos.first().key.toString()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}