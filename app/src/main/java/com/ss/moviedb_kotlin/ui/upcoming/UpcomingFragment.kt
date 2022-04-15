package com.ss.moviedb_kotlin.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.ss.moviedb_kotlin.data.repository.UpcomingRepository
import com.ss.moviedb_kotlin.databinding.UpcomingFragmentBinding
import com.ss.moviedb_kotlin.db.remote.MovieDatabase
import com.ss.moviedb_kotlin.network.MovieDbApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UpcomingFragment : Fragment() {
    private var _binding: UpcomingFragmentBinding? = null;
    private val binding get() = _binding!!
    private val viewModel: UpcomingViewModel by lazy {
        ViewModelProvider(
            requireActivity(), UpcomingViewModelFactory(
                UpcomingRepository(
                    MovieDbApi,
                    MovieDatabase.getInstance(requireContext())
                )
            )
        ).get(UpcomingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UpcomingFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setViewModel()
    }

    private fun initView() {
        val pagingAdapter = UpcomingAdapter()
        // * Recycler view
        binding.recyclerViewUpcoming.apply {
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

    private fun setViewModel() {
        // TODO Navigate to detail fragment
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}