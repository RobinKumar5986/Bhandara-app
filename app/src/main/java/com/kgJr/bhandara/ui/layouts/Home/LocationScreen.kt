package com.kgJr.bhandara.ui.layouts.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.kgJr.bhandara.MainApplication
import com.kgJr.bhandara.R
import com.kgJr.bhandara.UtilFunctions.detectLocation
import com.kgJr.bhandara.UtilFunctions.openAppSettings
import com.kgJr.bhandara.data.local.DbStates
import com.kgJr.bhandara.data.local.tables.LocationEntity
import com.kgJr.bhandara.ui.NavDestination.NavigationGraph.UiGraph.HomeScreenId
import com.kgJr.bhandara.ui.layouts.UiUtils.CenteredProgressView
import com.kgJr.bhandara.ui.theme.DarkGrey
import com.kgJr.bhandara.ui.theme.SpaceExtremeHuge
import com.kgJr.bhandara.ui.theme.TrueWhite
import com.kgJr.bhandara.ui.theme.primary_button_color

@SuppressLint("CommitPrefEdits")
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    nav: (HomeScreenId) -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    val sharedPreferences = MainApplication.sharedPreferences
    val userDataPref = MainApplication.userDataPref
    var isLoading by remember { mutableStateOf(false) }
    val isLogin = userDataPref.getBoolean("isLoggedIn", false)

    var globalLat by remember { mutableStateOf(0.0) }
    var globalLong by remember { mutableStateOf(0.0) }
    ObserveLocationInput(
        viewModel = viewModel,
    ){
        if(isLogin ) {
            userDataPref.edit().putBoolean("isLoggedIn", true).apply()
            nav(HomeScreenId.HOME_SCREEN)
        }
    }
    LaunchedEffect(locationPermissionState.status.isGranted) {
        if (locationPermissionState.status.isGranted) {
            isLoading = true
            detectLocation(fusedLocationClient, context) { lat, long ->

                isLoading = false
                if (lat != null && long != null) {
                    sharedPreferences.edit().putInt("denial_count", 0).apply()
                    globalLat = lat
                    globalLong = long
                    viewModel.insertLocation(location = LocationEntity(latitude = lat, longitude = long))
                    if(isLogin){
                        nav(HomeScreenId.HOME_SCREEN)
                    }
                }
            }
        }
    }
    if(!viewModel.isLoading.collectAsState().value) {
        Box(
            modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
                // Center Image
                Image(
                    painter = painterResource(id = R.drawable.map),
                    contentDescription = "Location Image",
                    modifier = Modifier
                        .padding(top = SpaceExtremeHuge)
                        .size(200.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Detect My Location Button
                    Button(
                        onClick = {
                            if (locationPermissionState.status.isGranted) {
                                isLoading = true

                                detectLocation(fusedLocationClient, context) { lat, long ->
                                    isLoading = false
                                    if (lat != null && long != null) {
                                        sharedPreferences.edit().putInt("denial_count", 0).apply()
                                        globalLat = lat
                                        globalLong = long
                                        viewModel.insertLocation(
                                            location = LocationEntity(
                                                latitude = lat,
                                                longitude = long
                                            )
                                        )
                                    }
                                }
                            } else {
                                val denialCount = sharedPreferences.getInt("denial_count", 0) + 1
                                sharedPreferences.edit().putInt("denial_count", denialCount).apply()
                                if (denialCount > 2) {
                                    openAppSettings(context)
                                } else {
                                    locationPermissionState.launchPermissionRequest()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary_button_color,
                            contentColor = TrueWhite,
                            disabledContainerColor = DarkGrey,
                            disabledContentColor = TrueWhite
                        )
                    ) {
                        if (!isLoading) {
                            Text(
                                text = "Detect My Location",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
    }else{
        CenteredProgressView(message = "Registering the user please be patient...")
    }
}
@ExperimentalMaterial3Api
@Composable
fun ObserveLocationInput(
    viewModel: HomeViewModel,
    onSuccess: () -> Unit
) {
    val dbState =  viewModel.dbState.collectAsStateWithLifecycle()
    LaunchedEffect(dbState.value) {
        if(dbState.value == DbStates.SUCCESS){
            onSuccess()
        }
    }
}
