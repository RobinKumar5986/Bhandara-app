package com.kgJr.bhandara.ui.layouts.Auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.kgJr.bhandara.MainApplication
import com.kgJr.bhandara.ui.NavDestination.AuthScreenGraph
import com.kgJr.bhandara.ui.NavDestination.NavigationGraph.AuthScreenId
import com.kgJr.bhandara.ui.layouts.Home.HomeViewModel
import com.kgJr.bhandara.ui.layouts.UiUtils.OTPTextField
import com.kgJr.bhandara.ui.theme.DarkGrey
import com.kgJr.bhandara.ui.theme.SpaceExtremeHuge
import com.kgJr.bhandara.ui.theme.body
import com.kgJr.bhandara.ui.theme.disable_button_color
import com.kgJr.bhandara.ui.theme.primary_button_color

@Composable
fun OtpVerificationScreen(
    args: AuthScreenGraph.OtpVerificationScreen,
    nav: (AuthScreenId) ->Unit
) {
    var value by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val length = 6
    val viewModel: HomeViewModel = hiltViewModel()
    val regUserInfo = viewModel.regUserInfo.collectAsState().value
    val error = viewModel.error.collectAsState().value
    LaunchedEffect(Unit){
        viewModel.clearData()
    }
    LaunchedEffect(error){
        if(error != null){
            Toast.makeText(context,"Error: ${error}",Toast.LENGTH_SHORT).show()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = SpaceExtremeHuge),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Otp Sent to phone Number",
                style = MaterialTheme.typography.labelMedium.copy(fontSize = body),
                color = DarkGrey,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, bottom = 8.dp)
            )
            Text(
                text = args.phoneNumber.ifEmpty { "99999999999" },
                style = MaterialTheme.typography.labelMedium.copy(fontSize = body),
                color = DarkGrey,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 16.dp)
            )
            OTPTextField(
                length = length,
                value = value,
                onValueChange = { newValue ->
                    value = newValue
                    if (value.length == length) {
                        focusManager.clearFocus()
                    }
                },
            )
        }

        // Floating Button
        androidx.compose.material3.FloatingActionButton(
            onClick = {
                if (value.length != length) {
                    Toast.makeText(context, "Enter a valid OTP.", Toast.LENGTH_SHORT).show()
                } else {
                    val verificationId = args.userId

                    val credential = PhoneAuthProvider.getCredential(verificationId, value)

                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "OTP Verified Successfully", Toast.LENGTH_SHORT).show()
                                val sharedPreferences = MainApplication.userDataPref
                                val editor = sharedPreferences.edit()
                                editor.putString("phoneNumber", args.phoneNumber)
                                editor.putString("userId", args.userId)
                                editor.putBoolean("isLoggedIn", true)
                                editor.apply()
                                nav(AuthScreenId.HOME_SCREEN)
                                //TODO: If needed need to go with this register user api..(note there is some error as of now)
//                                val userInfo = UserInfoDto(
//                                    id = null,
//                                    phoneNo = args.phoneNumber,
//                                    userUid = args.userId,
//                                    latitude = 0.0,
//                                    longitude = 0.0,
//                                    city = null ,
//                                    state = null ,
//                                    country = null,
//                                    createdOn = null,
//                                    updatedOn = null
//                                )
//                                viewModel.registerUser(userInfo)
                            } else {
                                Toast.makeText(context, "OTP Verification Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
            ,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .imePadding()
                .padding(16.dp),
            containerColor = if (value.length != length) disable_button_color else primary_button_color
        ) {
            Text(
                "Verify Otp",
                modifier = Modifier.padding(all = 16.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
