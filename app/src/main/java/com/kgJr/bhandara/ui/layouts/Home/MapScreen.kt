package com.kgJr.bhandara.ui.layouts.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.kgJr.bhandara.R
import com.kgJr.bhandara.ui.layouts.utils.generateCurvedPolyline
import com.kgJr.bhandara.ui.layouts.utils.openGoogleMaps
import com.kgJr.bhandara.ui.layouts.utils.refocusCamera
import com.kgJr.bhandara.ui.layouts.utils.vectorDrawableToBitmap
import com.kgJr.bhandara.ui.theme.DeepPink
import com.kgJr.bhandara.ui.theme.MediumGray

@Composable
fun MapScreen(
    srcLat: Double,
    srcLng: Double,
    dstLat: Double,
    dstLng: Double,
    srcTitle: String,
    srcDescription: String,
    dstTitle: String,
    dstDescription: String
) {
    val srcLocation = LatLng(srcLat, srcLng)
    val dstLocation = LatLng(dstLat, dstLng)
    val context = LocalContext.current
    val mapStyle = remember {
        MapStyleOptions.loadRawResourceStyle(context, R.raw.uber_style_map1)
    }
    val cameraPositionState = rememberCameraPositionState()

    val curvedPath = generateCurvedPolyline(srcLocation, dstLocation, curvatureFactor = -0.2)
    val srcMarker = vectorDrawableToBitmap(context, R.drawable.src_man, 40f)
    val dstMarker = vectorDrawableToBitmap(context, R.drawable.bhandara_icon, 35f)
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(mapStyleOptions = mapStyle),
            onMapLoaded = {
                refocusCamera(cameraPositionState, srcLocation, dstLocation)
            }
        ) {
            //source
            Marker(
                state = rememberMarkerState(position = srcLocation),
                title = srcTitle,
                snippet = srcDescription,
                icon = BitmapDescriptorFactory.fromBitmap(srcMarker)

            )
            //destination
            Marker(
                state = rememberMarkerState(position = dstLocation),
                title = dstTitle,
                snippet = dstDescription,
                icon = BitmapDescriptorFactory.fromBitmap(dstMarker)
            )

            // Curved Line
            Polyline(
                points = curvedPath,
                color = MediumGray,
                width = 10f
            )

            // Straight Line (50% transparent)
            Polyline(
                points = listOf(srcLocation, dstLocation),
                color = MediumGray.copy(alpha = 0.2f),
                width = 10f
            )
        }

        // Buttons at the top
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Focus Button
            Button(onClick = {
                refocusCamera(cameraPositionState, srcLocation, dstLocation)
            }) {
                Row {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Focus Icon",
                        tint = DeepPink
                    )
                }
            }

            // Direction Button
            Button(onClick = {
                openGoogleMaps(context, srcLocation, dstLocation)
            }) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.directions),
                        contentDescription = "Directions Icon",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(DeepPink)
                    )
                }
            }
        }
    }
}


