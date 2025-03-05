package com.kgJr.bhandara.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(

    primary = Color(0xFFFF80B0), // pink_primary
    secondary = Color(0xFFB04A69), // accent_dark
    tertiary = Color(0xFFD46A9E), // pink_dark
    background = Color(0xFF2E2E35), // background_dark
    surface = Color(0xFF2E2E35), // background_dark
    onPrimary = Color(0xFFFFFFFF), // white
    onSecondary = Color(0xFFFFFFFF), // white
    onTertiary = Color(0xFFFFFFFF), // white
    onBackground = Color(0xFFFFE3E8), // accent_light
    onSurface = Color(0xFFFFE3E8) // accent_light
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFFA6C9), // pink_light
    secondary = Color(0xFFFFE3E8), // accent_light
    tertiary = Color(0xFFFF80B0), // pink_primary
    background = Color(0xFFFFF8F9), // background_light
    surface = Color(0xFFFFF8F9), // background_light
    onPrimary = Color(0xFF000000), // black
    onSecondary = Color(0xFF000000), // black
    onTertiary = Color(0xFF000000), // black
    onBackground = Color(0xFF1C1B1F), // dark text
    onSurface = Color(0xFF1C1B1F) // dark text
)

@Composable
fun BhandaraStartActivity(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val window = (LocalContext.current as? Activity)?.window
    window?.let {
        val statusBarColor = DarkColorScheme.tertiary
        it.statusBarColor = statusBarColor.toArgb()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}