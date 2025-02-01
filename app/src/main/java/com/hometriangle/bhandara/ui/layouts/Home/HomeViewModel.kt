package com.hometriangle.bhandara.ui.layouts.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hometriangle.bhandara.MainApplication
import com.hometriangle.bhandara.databaseUtils.DbStates
import com.hometriangle.bhandara.databaseUtils.roomDBS3.AppDatabase
import com.hometriangle.bhandara.databaseUtils.tablesS1.LocationEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    applicationDb: AppDatabase = MainApplication.applicationDB
): ViewModel() {
    private val locationDao = applicationDb.locationDao()

    private val _dbState = MutableStateFlow<DbStates>(DbStates.LOADING)
    val dbState: StateFlow<DbStates>
        get() = _dbState

    // LiveData for observing location data
    private val _locations = MutableStateFlow<List<LocationEntity>>(emptyList())
    val locations: StateFlow<List<LocationEntity>>
        get() = _locations

    // LiveData for handling errors
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?>
        get() = _error
    init {
        getAllLocation()
    }
    fun getAllLocation() {
        viewModelScope.launch {
            locationDao.getLocation()
                .catch { exception ->
                    _error.value = "Error fetching locations: ${exception.message}"
                }
                .collectLatest { locationList ->
                    _locations.value = locationList
                }
        }
    }

    fun insertLocation(location: LocationEntity) {
        viewModelScope.launch {
            _dbState.value = (DbStates.LOADING)
            try {
                locationDao.insertLocation(location)
                _dbState.value = (DbStates.SUCCESS)
            } catch (exception: Exception) {
                _error.value = ("Error inserting location: ${exception.message}")
                _dbState.value = (DbStates.ERROR)
            }
        }
    }
}