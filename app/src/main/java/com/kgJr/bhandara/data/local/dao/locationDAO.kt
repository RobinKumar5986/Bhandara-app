package com.kgJr.bhandara.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kgJr.bhandara.data.local.tables.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao{

    @Upsert
    suspend fun insertLocation(location: LocationEntity)

    @Query("SELECT * FROM location")
    fun getLocation(): Flow<List<LocationEntity>>

}