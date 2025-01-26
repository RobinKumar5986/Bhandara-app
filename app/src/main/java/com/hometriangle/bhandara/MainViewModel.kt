package com.hometriangle.bhandara

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hometriangle.bhandara.databaseUtils.roomDBS3.AppDatabase
import com.hometriangle.bhandara.databaseUtils.tablesS1.LocationEntity

data class MainViewModel(
    val applicationDb: AppDatabase = MainApplication.applicationDB
): ViewModel(){
    val locationDb = applicationDb.locationDao()
}