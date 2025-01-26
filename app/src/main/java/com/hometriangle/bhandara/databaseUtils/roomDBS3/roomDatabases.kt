package com.hometriangle.bhandara.databaseUtils.roomDBS3

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hometriangle.bhandara.databaseUtils.daoS2.LocationDao
import com.hometriangle.bhandara.databaseUtils.tablesS1.LocationEntity

@Database(entities = [LocationEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){

    // @NOTE: this contains all the names of the db which are going to be present.
    companion object {
        const val  APPLICATION_DB= "Application_DB"
    }
    abstract fun locationDao() : LocationDao
}