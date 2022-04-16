package com.ss.moviedb_kotlin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviedb_kotlin.data.repository.PopularRepository
import com.ss.moviedb_kotlin.databinding.HomeFragmentBinding
import com.ss.moviedb_kotlin.db.remote.MovieDatabase
import com.ss.moviedb_kotlin.network.MovieDbApi
import com.ss.moviedb_kotlin.ui.popular.PopularAdapter
import com.ss.moviedb_kotlin.ui.popular.PopularViewModel
import com.ss.moviedb_kotlin.ui.popular.PopularViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val popularViewModel: PopularViewModel by activityViewModels {
        PopularViewModelFactory(
            PopularRepository(
                MovieDbApi,
                MovieDatabase.getInstance(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setViewModel()
    }

    private fun initView() {
        val popularPagingAdapter = PopularAdapter()
        // * Recycler view
        binding.recyclerViewPopular.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = popularPagingAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                popularViewModel.pagingDataFlow
                    .collectLatest { pagingData ->
                        popularPagingAdapter.submitData(pagingData)
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