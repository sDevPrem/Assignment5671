package com.sdevprem.roadcastassignment.presentation.location.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object PermissionUtils {
    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun checkAndRequestLocationPermission(
        requestPermissionLauncher: ActivityResultLauncher<Array<String>>,
        activity: Activity,
        onPermissionAlreadyGranted: () -> Unit,
    ) {
        when {
            locationPermissions.all {
                ContextCompat.checkSelfPermission(
                    activity,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            } -> {
                onPermissionAlreadyGranted()
            }

            locationPermissions.any {
                ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
            } -> {
                showLocationPermissionRationale(activity) {
                    requestPermissionLauncher.launch(locationPermissions)
                }
            }
            else -> {
                requestPermissionLauncher.launch(locationPermissions)
            }
        }
    }

    private fun showLocationPermissionRationale(
        context: Context,
        onGrantPermission: () -> Unit,
    ) {
        MaterialAlertDialogBuilder(context)
            .setMessage("Please provide location permission to get location updates")
            .setPositiveButton("Grant") { dialog, _ ->
                onGrantPermission()
                dialog.dismiss()
            }
            .setNegativeButton("Deny") { _,_ ->
                Toast.makeText(
                    context,
                    "Please give location permission to get location updates",
                    Toast.LENGTH_LONG
                ).show()
            }.show()

    }

    fun handlePermissionResults(
        permissionResults: Map<String,Boolean>,
        context: Context,
        onPermissionGranted: () -> Unit,
    ) {
        if (permissionResults.values.all { isGranted -> isGranted }) {
            onPermissionGranted()
        } else {
            showLocationPermissionRationale(context) {
                Toast.makeText(
                    context,
                    "Please give location permission to get location updates",
                    Toast.LENGTH_LONG
                ).show()
                openAppSetting(context)
            }
        }
    }

    private fun openAppSetting(context: Context) {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        ).also(context::startActivity)
    }
}