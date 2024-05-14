package com.sdevprem.roadcastassignment.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sdevprem.roadcastassignment.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    repository: MovieRepository
) : ViewModel() {

    val topRatedMovies = repository.getTopRatedMovies()
        .cachedIn(viewModelScope)
}