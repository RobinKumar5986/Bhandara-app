package com.kgJr.bhandara.data.local.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey
    val id: Int = 1, //Fixed Id (only contain one location at a time)
    val latitude: Double,
    val longitude: Double
)