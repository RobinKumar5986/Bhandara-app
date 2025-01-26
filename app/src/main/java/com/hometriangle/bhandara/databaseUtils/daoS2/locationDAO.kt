package com.hometriangle.bhandara.databaseUtils.daoS2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.hometriangle.bhandara.databaseUtils.tablesS1.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao{

    @Upsert
    suspend fun insertLocation(location: LocationEntity)

//    @Query("SELECT * FROM location")
//    suspend fun getLocation(): LiveData<List<LocationEntity>>

}