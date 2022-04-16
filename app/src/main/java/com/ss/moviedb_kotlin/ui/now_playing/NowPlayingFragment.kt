package com.ss.moviedb_kotlin.ui.now_playing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
    private val viewModel: NowPlayingViewModel by activityViewModels {
        NowPlayingViewModelFactory(
            NowPlayingRepository(
                MovieDbApi,
                MovieDatabase.getInstance(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NowPlayingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        val pagingAdapter = NowPlayingAdapter()
        // * Recycler view
        binding.recyclerViewNowPlaying.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = pagingAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagingDataFlow
                    .collectLatest { pagingData ->
                        pagingAdapter.submitData(pagingData)
                    }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}