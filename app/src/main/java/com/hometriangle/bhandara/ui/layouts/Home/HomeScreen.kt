package com.hometriangle.bhandara.ui.layouts.Home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.hometriangle.bhandara.R
import com.hometriangle.bhandara.ui.theme.SoftPink

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = HomeViewModel(),
    nav: () -> Unit // Later will be used for navigation
) {
    val locations by viewModel.locations.collectAsState()
    val context = LocalContext.current
    if (locations.isNotEmpty()) {
        val singapore = LatLng(locations.first().latitude, locations.first().longitude)
        val singaporeMarkerState = rememberMarkerState(position = singapore)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(singapore, 10f)
        }
        val mapStyle = remember {
            MapStyleOptions.loadRawResourceStyle(context, R.raw.uber_style_map1) // Load from res/raw/map_style.json
        }


        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color.Gray)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        GoogleMap(
                            modifier = Modifier.fillMaxSize(),
                            cameraPositionState = cameraPositionState,
                            uiSettings = MapUiSettings(zoomControlsEnabled = false), // Hide + - buttons
                            properties = MapProperties(mapStyleOptions = mapStyle)
                        ) {
                            Marker(
                                state = singaporeMarkerState,
                                title = "India",
                                snippet = "Marker in Singapore",
                            )
                        }

                        Button(
                            onClick = {
                                cameraPositionState.move(
                                    CameraUpdateFactory.newLatLngZoom(singapore, 10f)
                                )
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SoftPink),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                        ) {
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Recenter")
                        }
                    }
                }
            }

            FloatingActionButton(
                onClick = { /* Handle Add Click */ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}
