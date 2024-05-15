package com.sdevprem.roadcastassignment.presentation.location

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdevprem.roadcastassignment.data.location.LocationTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    locationTracker: LocationTracker,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val userLocation = savedStateHandle.getStateFlow(REQUEST_LOCATION, true)
        .filter { it }
        .flatMapLatest {
            savedStateHandle[REQUEST_LOCATION] = false
            locationTracker.location
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(10000),
            null
        )

    fun requestLocationUpdate() {
        savedStateHandle[REQUEST_LOCATION] = true
    }

    companion object {
        private const val REQUEST_LOCATION = "location_request"
    }
}