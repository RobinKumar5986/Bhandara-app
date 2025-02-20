package com.hometriangle.localModel


data class MapScreenModel(
    val srcLat: Double,
    val srcLng: Double,
    val dstLat: Double,
    val dstLng: Double,
    val srcTitle: String,
    val srcDescription: String,
    val dstTitle: String,
    val dstDescription: String
)