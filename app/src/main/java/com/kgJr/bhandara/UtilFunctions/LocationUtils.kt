package com.kgJr.bhandara.UtilFunctions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient

// Function to detect location
@SuppressLint("MissingPermission")
fun detectLocation(
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    latLong:(Double?,Double?) -> Unit
) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
            latLong(latitude,longitude)
        } else {
            latLong(null,null)
            Toast.makeText(context, "Unable to fetch location. Try again.", Toast.LENGTH_SHORT).show()
        }
    }.addOnFailureListener {
        latLong(null,null)
        Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
    }
}

// Function to open app settings
fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}