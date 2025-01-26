package com.hometriangle.bhandara

import android.app.Activity
import android.app.Application
import androidx.room.Room
import com.hometriangle.bhandara.databaseUtils.roomDBS3.AppDatabase

class MainApplication: Application() {
    /**
     * @author: note this is the activity which is going to be present the entire time
     * till the application is alive.
     * here we are going to declare all the db and other variables which we require during the entire
     * application processing.
     */

    companion object{
        lateinit var applicationDB: AppDatabase
    }
    @Override
    override fun onCreate() {
        super.onCreate()

        //location table
        applicationDB = Room.databaseBuilder(
            context = applicationContext,
            klass = AppDatabase::class.java,
            name = AppDatabase.APPLICATION_DB
        ).build()
    }
}