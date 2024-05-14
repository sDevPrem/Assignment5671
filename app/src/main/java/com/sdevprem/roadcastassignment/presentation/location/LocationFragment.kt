package com.sdevprem.roadcastassignment.presentation.location

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.sdevprem.roadcastassignment.R
import com.sdevprem.roadcastassignment.data.location.LocationTracker
import com.sdevprem.roadcastassignment.databinding.FragmentLocationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationFragment: Fragment() {

    private lateinit var binding: FragmentLocationBinding
    private val viewModel by viewModels<LocationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentLocationBinding.inflate(
            inflater,
            container,
            false
        ).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkAndRequestLocationSetting(requireActivity())

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userLocation
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .filterNotNull()
                .collectLatest {
                    binding.locationText.text = "lat = ${it.latitude} long = ${it.longitude}"
                }
        }
    }

    private fun checkAndRequestLocationSetting(activity: Activity) {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            LOCATION_UPDATE_INTERVAL_IN_MILLIS
        ).build()

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(activity)

        client.checkLocationSettings(builder.build())
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        exception.startResolutionForResult(
                            activity,
                            LOCATION_ENABLE_REQUEST_CODE
                        )
                    } catch (_: IntentSender.SendIntentException) {

                    }
                }
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOCATION_ENABLE_REQUEST_CODE && resultCode != Activity.RESULT_OK) {
            Toast.makeText(
                requireContext(),
                "Please enable GPS to get proper running statistics.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        private const val LOCATION_ENABLE_REQUEST_CODE = 5234
        private const val LOCATION_UPDATE_INTERVAL_IN_MILLIS = 500L
    }
}