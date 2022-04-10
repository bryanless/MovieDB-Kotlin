package com.ss.moviedb_kotlin.ui.now_playing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ss.moviedb_kotlin.data.repository.NowPlayingRepository
import com.ss.moviedb_kotlin.databinding.NowPlayingFragmentBinding
import com.ss.moviedb_kotlin.db.remote.MovieDatabase
import com.ss.moviedb_kotlin.network.MovieDbApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NowPlayingFragment : Fragment() {

    private var _binding: NowPlayingFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NowPlayingViewModel by lazy {
        ViewModelProvider(
            requireActivity(), NowPlayingViewModelFactory(
                NowPlayingRepository(
                    MovieDbApi,
                    MovieDatabase.getInstance(requireContext())
                )
            )
        ).get(NowPlayingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NowPlayingFragmentBinding.inflate(inflater, container, false)

        init()
        setViewModel()

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun setViewModel() {
        // TODO Navigate to detail fragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}