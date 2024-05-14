package com.sdevprem.roadcastassignment.presentation.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdevprem.roadcastassignment.data.location.LocationTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    locationTracker: LocationTracker
): ViewModel() {

    val userLocation = locationTracker.location
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(10000),
            null
        )
}