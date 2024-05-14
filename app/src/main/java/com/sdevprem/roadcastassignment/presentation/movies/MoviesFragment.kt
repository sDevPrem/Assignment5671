package com.sdevprem.roadcastassignment.presentation.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.sdevprem.roadcastassignment.databinding.FragmentMoviesBinding
import com.sdevprem.roadcastassignment.presentation.movies.adapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment: Fragment() {

    private lateinit var binding: FragmentMoviesBinding

    private val adapter = MoviesAdapter()
    private val viewModel by viewModels<MoviesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentMoviesBinding.inflate(
            inflater,
            container,
            false
        ).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        rvMovies.layoutManager = GridLayoutManager(requireContext(), 2)
        rvMovies.adapter = adapter
        topToolbar.title = "Top Rated Movies"

        lifecycleScope.launch {
            viewModel.topRatedMovies
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest {
                    adapter.submitData(it)
                }
        }
        return@with
    }

}