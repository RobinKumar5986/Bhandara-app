package com.hometriangle.bhandara.ui.layouts.Home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.hometriangle.bhandara.MainApplication
import com.hometriangle.bhandara.R
import com.hometriangle.bhandara.ui.layouts.utils.vectorDrawableToBitmap

@Composable
fun AllBhandaraMapScreen() {
    val context = LocalContext.current
    val allBhandara = MainApplication.bhandaraList
    val bhandaraIcon = vectorDrawableToBitmap(context, R.drawable.bhandara_icon, 35f)

    if (!allBhandara.isNullOrEmpty()) {
        val cameraPositionState = rememberCameraPositionState {
            val firstLocation = allBhandara.firstOrNull()
            if (firstLocation?.latitude != null && firstLocation.longitude != null) {
                position = CameraPosition.fromLatLngZoom(
                    LatLng(firstLocation.latitude, firstLocation.longitude), 5f
                )
            }
        }

        val mapStyle = remember {
            MapStyleOptions.loadRawResourceStyle(context, R.raw.uber_style_map1)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(mapStyleOptions = mapStyle),
        ) {
            allBhandara.forEach { bhandara ->
                if (bhandara.latitude != null && bhandara.longitude != null) {
                    Marker(
                        state = rememberMarkerState(
                            position = LatLng(bhandara.latitude, bhandara.longitude)
                        ),
                        title = bhandara.name ?: "Bhandara",
                        snippet = bhandara.description,
                        icon =  BitmapDescriptorFactory.fromBitmap(bhandaraIcon)
                    )
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No Bhandara Found")
        }
    }
}
