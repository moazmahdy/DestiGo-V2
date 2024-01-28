package com.mobilebreakero.common_ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.Locale
import java.util.concurrent.TimeUnit

lateinit var locationCallback: LocationCallback

lateinit var locationProvider: FusedLocationProviderClient
private var LOCATION_TAG: String = "Location"

@SuppressLint("MissingPermission")
@Composable
fun getUserLocation(context: Context): LatandLong {

    locationProvider = LocationServices.getFusedLocationProviderClient(context)

    var currentUserLocation by remember { mutableStateOf(LatandLong()) }

    DisposableEffect(key1 = locationProvider) {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {

                for (location in result.locations) {
                    // Update data class with location data
                    currentUserLocation = LatandLong(location.latitude, location.longitude)
                    Log.d(LOCATION_TAG, "${location.latitude},${location.longitude}")
                }



                locationProvider.lastLocation
                    .addOnSuccessListener { location ->
                        location?.let {
                            val lat = location.latitude
                            val long = location.longitude
                            currentUserLocation = LatandLong(latitude = lat, longitude = long)
                        }
                    }
                    .addOnFailureListener {
                        Log.e("Location_error", "${it.message}")
                    }

            }
        }
        locationUpdate()

        onDispose {
            stopLocationUpdate()
        }
    }
    return currentUserLocation

}

fun stopLocationUpdate() {
    try {
        val removeTask = locationProvider.removeLocationUpdates(locationCallback)
        removeTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(LOCATION_TAG, "Location Callback removed.")
            } else {
                Log.d(LOCATION_TAG, "Failed to remove Location Callback.")
            }
        }
    } catch (se: SecurityException) {
        Log.e(LOCATION_TAG, "Failed to remove Location Callback.. $se")
    }
}

@SuppressLint("MissingPermission")
fun locationUpdate() {
    locationCallback.let {

        val locationRequest: LocationRequest =
            LocationRequest.create().apply {
                interval = TimeUnit.SECONDS.toMillis(60)
                fastestInterval = TimeUnit.SECONDS.toMillis(30)
                maxWaitTime = TimeUnit.MINUTES.toMillis(2)
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
        locationProvider.requestLocationUpdates(
            locationRequest,
            it,
            Looper.getMainLooper()
        )
    }

}

data class LatandLong(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)


fun getReadableLocation(
    latitude: Double,
    longitude: Double,
    context: Context,
    onLocationRequest: (String) -> Unit
): String {
    var addressText = ""
    val geocoder = Geocoder(context, Locale.getDefault())

    try {

        val addresses = geocoder.getFromLocation(latitude, longitude, 1)

        if (addresses?.isNotEmpty() == true) {
            val address = addresses[0]
            val country = addresses[0].countryName
            val government = addresses[0].adminArea
            val city = addresses[0].locality
            val fullAddress = "$government, $country"
            addressText = "${address.getAddressLine(0)}, ${address.locality}"
            onLocationRequest(fullAddress)
            Log.d("geolocation", addressText)
        }

    } catch (e: IOException) {
        Log.d("geolocation", e.message.toString())

    }

    return addressText

}

