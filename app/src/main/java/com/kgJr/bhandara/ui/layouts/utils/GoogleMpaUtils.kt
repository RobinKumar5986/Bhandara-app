package com.kgJr.bhandara.ui.layouts.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.CameraPositionState

// Function to refocus the camera
fun refocusCamera(
    cameraPositionState: CameraPositionState,
    src: LatLng,
    dst: LatLng
) {
    val bounds = LatLngBounds.builder()
        .include(src)
        .include(dst)
        .build()
    cameraPositionState.move(CameraUpdateFactory.newLatLngBounds(bounds, 200))
}

// Function to open Google Maps for directions
fun openGoogleMaps(context: Context, src: LatLng, dst: LatLng) {
    val uri = "https://www.google.com/maps/dir/?api=1&origin=${src.latitude},${src.longitude}&destination=${dst.latitude},${dst.longitude}&travelmode=driving"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    intent.setPackage("com.google.android.apps.maps")
    context.startActivity(intent)
}

/**
 * Generates a curved polyline between two LatLng points.
 * @param start Start LatLng point.
 * @param end End LatLng point.
 * @param curvatureFactor Adjusts the curve height (positive for upward, negative for downward).
 */
fun generateCurvedPolyline(start: LatLng, end: LatLng, curvatureFactor: Double): List<LatLng> {
    val midPoint = SphericalUtil.interpolate(start, end, 0.5)
    val distance = SphericalUtil.computeDistanceBetween(start, end)

    // Move the midpoint further away for a higher curve
    val direction = SphericalUtil.computeHeading(start, end)
    val curveMidPoint = SphericalUtil.computeOffset(midPoint, distance * curvatureFactor, direction + 90)

    val points = mutableListOf<LatLng>()
    val numPoints = 20
    for (i in 0..numPoints) {
        val fraction = i / numPoints.toDouble()
        val interpolatedPoint = SphericalUtil.interpolate(
            SphericalUtil.interpolate(start, curveMidPoint, fraction),
            SphericalUtil.interpolate(curveMidPoint, end, fraction),
            fraction
        )
        points.add(interpolatedPoint)
    }
    return points
}

@Composable
fun vectorDrawableToBitmap(context: Context, drawableResId: Int, sizeDp: Float): Bitmap {
    val vectorDrawable = ContextCompat.getDrawable(context, drawableResId)

    val density = LocalDensity.current.density
    val sizePx = (sizeDp * density).toInt()

    vectorDrawable?.setBounds(0, 0, sizePx, sizePx)

    val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable?.draw(canvas)

    return bitmap
}