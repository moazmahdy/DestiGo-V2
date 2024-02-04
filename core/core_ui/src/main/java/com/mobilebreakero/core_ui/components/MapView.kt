package com.mobilebreakero.core_ui.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.mobilebreakero.core_ui.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun MapView(
    onLocationSelected: (String) -> Unit,
    getGovernment: (String) -> Unit,
    context: Context
) {
    var selectedMarker: Marker? by remember { mutableStateOf(null) }
    val geocoder = Geocoder(context)
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val buttonModifier = Modifier
        .width(80.dp)
        .height(80.dp)
        .padding(10.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        AndroidView(
            factory = { contextt ->
                val mapView = View.inflate(contextt, R.layout.map, null) as MapView
                mapView.onCreate(Bundle())
                mapView.onResume()
                mapView.getMapAsync { googleMap ->
                    googleMap.setOnMapClickListener { latLng ->
                        val latitude = latLng.latitude
                        val longitude = latLng.longitude

                        val newLocation = "$latitude,$longitude"

                        val addresses = geocoder.getFromLocation(latitude, longitude, 1)

                        if (!addresses.isNullOrEmpty()) {
                            val country = addresses[0].countryName
                            val government = addresses[0].adminArea
                            val fullAddress = "$government, $country"
                            onLocationSelected(fullAddress)
                            getGovernment(government)
                        } else {
                            onLocationSelected(newLocation)
                        }

                        selectedMarker?.remove()
                        selectedMarker = googleMap.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        )
                    }
                }
                mapView
            },
            modifier = Modifier
                .fillMaxSize(),
        )
        IconButton(
            onClick = {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                }
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.let { currentLocation ->
                            val newLocation =
                                "${currentLocation.latitude},${currentLocation.longitude}"
                            val addresses = geocoder.getFromLocation(
                                currentLocation.latitude,
                                currentLocation.longitude,
                                1
                            )
                            if (!addresses.isNullOrEmpty()) {
                                val country = addresses[0].countryName
                                val government = addresses[0].adminArea
                                val fullAddress = "$government, $country"
                                onLocationSelected(fullAddress)
                                if (government != null)
                                    getGovernment(government)
                                else {
                                    GlobalScope.launch(Dispatchers.Main) {
                                        getGovernment("Cairo")
                                    }
                                }
                            } else {
                                onLocationSelected(newLocation)
                            }
                            selectedMarker?.remove()
                        }
                    }
            },
            modifier = buttonModifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                tint = Color.White,
                modifier = buttonModifier,
                contentDescription = "Current Location",
            )
        }
    }

}