package com.sdevprem.roadcastassignment.presentation.location

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.ktx.addMarker
import com.sdevprem.roadcastassignment.data.location.model.LocationPoint
import com.sdevprem.roadcastassignment.databinding.FragmentLocationBinding
import com.sdevprem.roadcastassignment.presentation.location.utils.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding
    private val viewModel by viewModels<LocationViewModel>()
    private var marker: Marker? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionResults ->
            PermissionUtils.handlePermissionResults(
                permissionResults = permissionResults,
                context = requireContext(),
                onPermissionGranted = {
                    checkAndRequestLocationSetting()
                    viewModel.requestLocationUpdate()
                }
            )
        }

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
        checkAndRequestLocationPermission()

        val mapFragment =
            childFragmentManager.findFragmentById(binding.map.id) as? SupportMapFragment
        mapFragment?.getMapAsync (::startUpdatingLocation)

        binding.findMyLocationFab.setOnClickListener {
            checkAndRequestLocationPermission()
        }

    }

    private fun checkAndRequestLocationPermission() {
        PermissionUtils.checkAndRequestLocationPermission(
            requestPermissionLauncher = requestPermissionLauncher,
            activity = requireActivity(),
            onPermissionAlreadyGranted = {
                checkAndRequestLocationSetting()
                viewModel.requestLocationUpdate()
            }
        )
    }

    private fun startUpdatingLocation(googleMap: GoogleMap) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userLocation
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .filterNotNull()
                .collectLatest { locationPoint ->
                    setLocationToMap(locationPoint,googleMap)
                }
        }
    }

    private fun setLocationToMap(locationPoint: LocationPoint, googleMap: GoogleMap) {
        val latLng = LatLng(locationPoint.latitude, locationPoint.longitude)
        marker?.let {
            it.position = latLng
        } ?: run {
            marker = googleMap.addMarker {
                position(latLng)
            }
        }
        moveCamera(latLng,googleMap)
    }

    private fun moveCamera(latLng: LatLng, googleMap: GoogleMap) {
        googleMap.animateCamera(
            CameraUpdateFactory.newCameraPosition(CameraPosition(latLng, 18.0f, 0F, 0F))
        )
    }

    private fun checkAndRequestLocationSetting() {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            LOCATION_UPDATE_INTERVAL_IN_MILLIS
        ).build()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())

        client.checkLocationSettings(builder.build())
            .addOnFailureListener(::requestLocationSetting)
    }

    private fun requestLocationSetting(exception:  Exception) {
        if (exception is ResolvableApiException) {
            try {
                exception.startResolutionForResult(
                    requireActivity(),
                    LOCATION_ENABLE_REQUEST_CODE
                )
            } catch (_: IntentSender.SendIntentException) {
                Toast.makeText(
                    requireContext(),
                    "Please enable GPS to get proper running statistics.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOCATION_ENABLE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.requestLocationUpdate()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enable GPS to get proper running statistics.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        private const val LOCATION_ENABLE_REQUEST_CODE = 5234
        private const val LOCATION_UPDATE_INTERVAL_IN_MILLIS = 500L
    }
}