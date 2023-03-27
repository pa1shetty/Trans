package com.example.trans.utillity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class LocationHelper @Inject constructor(
    @ActivityContext private val activity: Context
) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

    fun getCurrentLocation(
        onLocationReceived: (Location) -> Unit,
        error: () -> Unit,
    ) {
        if (checkLocationPermission()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    onLocationReceived(it)
                }
            }
        }
        else{
            error()
        }
    }

    fun checkLocationPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity as AppCompatActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            return false
        } else {
            return true
        }

    }
}