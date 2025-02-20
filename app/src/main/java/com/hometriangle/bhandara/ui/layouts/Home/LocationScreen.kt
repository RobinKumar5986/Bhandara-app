package com.hometriangle.bhandara.ui.layouts.Home

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.hometriangle.bhandara.MainApplication
import com.hometriangle.bhandara.R
import com.hometriangle.bhandara.UtilFunctions.detectLocation
import com.hometriangle.bhandara.UtilFunctions.openAppSettings
import com.hometriangle.bhandara.data.local.DbStates
import com.hometriangle.bhandara.data.local.tables.LocationEntity
import com.hometriangle.bhandara.data.models.UserInfoDto
import com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.UiGraph.HomeScreenId
import com.hometriangle.bhandara.ui.layouts.UiUtils.CenteredProgressView
import com.hometriangle.bhandara.ui.theme.DarkGrey
import com.hometriangle.bhandara.ui.theme.SpaceExtremeHuge
import com.hometriangle.bhandara.ui.theme.TrueWhite
import com.hometriangle.bhandara.ui.theme.primary_button_color

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
    var isNav by remember { mutableStateOf(false) }
    val regUser = viewModel.regUserInfo.collectAsState().value

    val phoneNumber = userDataPref.getString("phoneNumber", "")
    val userId = userDataPref.getString("userId", "")
    val isLogin = userDataPref.getBoolean("isLoggedIn", false)

    var globalLat by remember { mutableStateOf(0.0) }
    var globalLong by remember { mutableStateOf(0.0) }
    ObserveLocationInput(
        viewModel = viewModel,
        context = context
    ){
        if(!isNav && isLogin ) {
            userDataPref.edit().putBoolean("isLoggedIn", true).apply()
            nav(HomeScreenId.HOME_SCREEN)
        }else{
            val userInfo = UserInfoDto(
                id = null,
                phoneNo = phoneNumber,
                userUid = userId,
                latitude = globalLat,
                longitude = globalLong,
                city = null ,
                state = null ,
                country = null,
                createdOn = null,
                updatedOn = null
            )
            viewModel.registerUser(userInfo)
        }
    }
    LaunchedEffect(regUser) {
        if (regUser != null) {
            userDataPref.edit().putBoolean("isLoggedIn", true).apply()
            isNav = true
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
                    val userInfo = UserInfoDto(
                        id = null,
                        phoneNo = phoneNumber,
                        userUid = userId,
                        latitude = lat,
                        longitude = long,
                        city = null ,
                        state = null ,
                        country = null,
                        createdOn = null,
                        updatedOn = null
                    )
                    if(isLogin){
                        isNav = true
                        nav(HomeScreenId.HOME_SCREEN)
                    }else {
                        viewModel.registerUser(userInfo)
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

                    // Search Location Button
//                    OutlinedButton(
//                        onClick = {
//                            // TODO: Need to go for search location based using Google Map API.
//                            //@MARK: This feature is not available for now.
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(56.dp),
//                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
//                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
//                    ) {
//                        Text(
//                            text = "Search Location",
//                            color = MaterialTheme.colorScheme.primary
//                        )
//                    }
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
    context: Context,
    onSuccess: () -> Unit
) {
    val dbState =  viewModel.dbState.collectAsStateWithLifecycle()
    LaunchedEffect(dbState.value) {
        if(dbState.value == DbStates.SUCCESS){
            onSuccess()
        }
    }
}
