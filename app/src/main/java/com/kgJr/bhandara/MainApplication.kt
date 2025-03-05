package com.kgJr.bhandara

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.kgJr.bhandara.data.models.BhandaraDto
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    /**
     * @author: [Robin Kumar] note this is the activity which is going to be present the entire time
     * till the application is alive.
     * here we are going to declare all the db and other variables which we require during the entire
     * application processing.
     */

    companion object {
        lateinit var sharedPreferences: SharedPreferences
        lateinit var userDataPref: SharedPreferences
        var bhandaraList: List<BhandaraDto>? = null
        lateinit var bhandaraDto : BhandaraDto
    }

    @Override
    override fun onCreate() {
        super.onCreate()

        //shared pref for location check.
        sharedPreferences = getSharedPreferences("location_prefs", Context.MODE_PRIVATE)
        userDataPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)

    }
    fun saveInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }
}
