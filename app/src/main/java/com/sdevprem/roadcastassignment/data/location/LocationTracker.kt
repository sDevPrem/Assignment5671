package com.sdevprem.roadcastassignment.data.location

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.sdevprem.roadcastassignment.data.location.model.LocationPoint
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class LocationTracker constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) {

    companion object {
        private const val LOCATION_UPDATE_INTERVAL = 500L
    }

    private val locationRequest
        get() = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            LOCATION_UPDATE_INTERVAL
        ).build()

    val location
        @SuppressLint("MissingPermission")
        get() = callbackFlow {
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    val lastLocation = result.lastLocation
                    val locationPoint = LocationPoint(
                        latitude = lastLocation?.latitude ?: 0.0,
                        longitude = lastLocation?.longitude ?: 0.0
                    )
                    trySend(locationPoint)
                }
            }
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

            awaitClose {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            }
        }
}