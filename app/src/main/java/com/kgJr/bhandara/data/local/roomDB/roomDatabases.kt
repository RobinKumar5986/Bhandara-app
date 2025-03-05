package com.kgJr.bhandara.data.local.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kgJr.bhandara.data.local.dao.LocationDao
import com.kgJr.bhandara.data.local.tables.LocationEntity

@Database(entities = [LocationEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){

    // @NOTE: this contains all the names of the db which are going to be present.
    companion object {
        const val  APPLICATION_DB= "Application_DB"
    }
    abstract fun locationDao() : LocationDao
}