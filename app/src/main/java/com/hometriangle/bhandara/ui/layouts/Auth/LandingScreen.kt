package com.hometriangle.bhandara.ui.layouts.Auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hometriangle.bhandara.FirebaseUtils.OtpStatus
import com.hometriangle.bhandara.FirebaseUtils.initiateOtpVerification
import com.hometriangle.bhandara.R
import com.hometriangle.bhandara.ui.NavDestination.NavigationGraph.AuthScreenId
import com.hometriangle.bhandara.ui.layouts.UiUtils.FeaturesCard
import com.hometriangle.bhandara.ui.layouts.UiUtils.StandardTextFieldNewTwo
import com.hometriangle.bhandara.ui.theme.DarkGrey
import com.hometriangle.bhandara.ui.theme.ProfilePictureSizeLarge
import com.hometriangle.bhandara.ui.theme.SpaceHuge
import com.hometriangle.bhandara.ui.theme.SpaceMedium
import com.hometriangle.bhandara.ui.theme.SpaceSmall
import com.hometriangle.bhandara.ui.theme.TrueBlack
import com.hometriangle.bhandara.ui.theme.TrueWhite
import com.hometriangle.bhandara.ui.theme.defaultButtonColor
import com.hometriangle.bhandara.ui.theme.md_theme_light_primary

@Composable
fun LandingScreen(
    nav: (AuthScreenId, String,String) ->Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    val context = LocalContext.current
    var isGetOtp by remember { mutableStateOf(false) }
    val brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFA6C9),
            Color(0xFFFFDDE5),
            Color(0xffffffff)
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
            .padding(20.dp)
            .padding(top = ProfilePictureSizeLarge),
        contentAlignment = Alignment.Center

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(painter = painterResource(R.drawable.app_logo), contentDescription = "app logo")
            StandardTextFieldNewTwo(
                text = phoneNumber,
                onValueChange = {
                    phoneNumber =
                        if (it.isNotEmpty() && it.first() != '0') it.filter { char -> char.isDigit() } else ""
                },
                hint = stringResource(R.string.mobile_number),
                maxLength = 10,
                leadingIcon = R.drawable.indian_flag_logo,
                keyboardType = KeyboardType.Number
            )
            Spacer(modifier = Modifier.height(SpaceMedium))

            Button(
                onClick = {
                    if (phoneNumber.length == 10) {
                        isGetOtp = true
                        initiateOtpVerification(phoneNumber = phoneNumber, context = context) { status , userId->
                            isGetOtp = false
                            if (status == OtpStatus.OTP_SENT) {
                                userId?.let { nav(AuthScreenId.OTP_SCREEN, phoneNumber, it) }
                            }
                        }
                    } else {
                        Toast.makeText(context, "Enter a valid 10-digit number", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = !isGetOtp,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = defaultButtonColor,
                    contentColor = TrueWhite,
                    disabledContainerColor = DarkGrey,
                    disabledContentColor = TrueWhite
                )
            ) {
                if (!isGetOtp) {
                    Text(
                        text = "Get verification code",
                        style = MaterialTheme.typography.bodyMedium.copy(color = TrueWhite),
                        modifier = Modifier.padding(vertical = SpaceSmall)
                    )
                } else {
                    CircularProgressIndicator(
                        color = TrueWhite,
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .size(MaterialTheme.typography.bodyLarge.fontSize.value.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(SpaceSmall))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpaceHuge)
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = md_theme_light_primary
                )
                Text(
                    text = "Why Choose us",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = SpaceSmall)
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = md_theme_light_primary
                )
            }

            FeaturesCard()
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Absolute.Right) {
                Button(
                    onClick = {
                        //TODO: Function for on Skip..
                    },
                    shape = MaterialTheme.shapes.large,
                    border = BorderStroke(1.dp, TrueBlack),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = Modifier
                        .padding(vertical = SpaceSmall)
                        .padding(end = SpaceMedium, bottom = SpaceMedium)
                ) {
                    Text(
                        text = "Skip",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color =  TrueBlack,
                            fontWeight = FontWeight.ExtraLight
                        ),
                    )
                }
            }
        }
    }
}

