package com.example.trans.utillity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class LocationPermissionChecker(
    @ActivityContext private val activity: AppCompatActivity,
) {

    fun checkLocationPermission(onPermissionCheck: (Boolean) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission not yet granted
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission already granted
            onPermissionCheck(true)
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        onPermissionCheck: (Boolean) -> Unit
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                onPermissionCheck(true)
            } else {
                // Permission denied
                onPermissionCheck(false)
            }
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }
}