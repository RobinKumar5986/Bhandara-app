package com.kgJr.bhandara.ui.layouts.Splash

import android.content.Context
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kgJr.bhandara.MainApplication
import com.kgJr.bhandara.ui.theme.ProfilePictureSizeMedium
import com.kgJr.bhandara.ui.theme.defaultButtonColor

@Composable
fun ServerStarter(nav: (Context) -> Unit) {
    val viewModel: ServerViewModel = hiltViewModel()
    val brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFA6C9),
            Color(0xFFFFDDE5),
            Color(0xffffffff)
        )
    )

    // Start the server process (with retries) when this composable enters composition.
    LaunchedEffect(Unit) {
        viewModel.startServer()
    }

    val context = LocalContext.current
    val response = viewModel.responseMsg.collectAsState().value
    val errMsg = viewModel.errMsg.collectAsState().value

    // When the server is up, navigate to the next screen.
    LaunchedEffect(response) {
        if (response != null && response == 1) {
            MainApplication.userDataPref.edit().putInt("response_key",response).apply()
            nav(context)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
            .padding(20.dp)
            .padding(top = ProfilePictureSizeMedium),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Starting the Server this may take up to 5-min. Please wait and enjoy the game below...\n${errMsg ?: ""}",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Animated Progress Bar
            val progressAnimation = rememberInfiniteTransition()
            val progress by progressAnimation.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = defaultButtonColor,
                backgroundColor =  Color.Transparent
            )

            Spacer(modifier = Modifier.height(16.dp))
            FlappyBirdGame()
        }
    }
}

