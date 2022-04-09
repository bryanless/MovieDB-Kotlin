package com.ss.moviedb_kotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ss.moviedb_kotlin.data.repository.NowPlayingRepository
import com.ss.moviedb_kotlin.databinding.NowPlayingFragmentBinding
import com.ss.moviedb_kotlin.db.remote.MovieDatabase
import com.ss.moviedb_kotlin.network.MovieDbApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NowPlayingFragment : Fragment() {

    private var _binding: NowPlayingFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NowPlayingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NowPlayingFragmentBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            requireActivity(), NowPlayingViewModelFactory(
                NowPlayingRepository(
                    MovieDbApi.retrofitService,
                    MovieDatabase.getInstance(requireContext())
                )
            )
        ).get(NowPlayingViewModel::class.java)

        init()

        return binding.root
    }

    private fun init() {
        val pagingAdapter = NowPlayingAdapter()
        // * Recycler view
        binding.recycleViewNowPlaying.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
//            layoutManager = LinearLayoutManager(requireContext())
            adapter = pagingAdapter
        }

        // * Lifecycle
//        binding.lifecycleOwner = this

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}