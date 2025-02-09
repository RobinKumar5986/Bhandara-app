package com.hometriangle.bhandara

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hometriangle.bhandara.data.local.dao.LocationDao
import com.hometriangle.bhandara.data.local.roomDB.AppDatabase
import com.hometriangle.bhandara.data.local.tables.LocationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var locationDao: LocationDao
) : ViewModel() {
    // LiveData for observing location data
    private val _locations = MutableLiveData<List<LocationEntity>>()
    val locations: LiveData<List<LocationEntity>>
        get() = _locations

    // LiveData for handling errors
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private fun getAllLocation() {
        viewModelScope.launch {
            locationDao.getLocation()
                .catch { exception ->
                    _error.postValue("Error fetching locations: ${exception.message}")
                }
                .collectLatest { locationList ->
                    _locations.postValue(locationList)
                }
        }
    }

    fun insertLocation(location: LocationEntity) {
        viewModelScope.launch {
            try {
                locationDao.insertLocation(location)
            } catch (exception: Exception) {
                _error.postValue("Error inserting location: ${exception.message}")
            }
        }
    }
}