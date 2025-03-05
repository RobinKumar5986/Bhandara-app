package com.kgJr.bhandara.ui.layouts.Home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kgJr.bhandara.data.local.DbStates
import com.kgJr.bhandara.data.local.dao.LocationDao
import com.kgJr.bhandara.data.local.tables.LocationEntity
import com.kgJr.bhandara.data.models.BhandaraDto
import com.kgJr.bhandara.data.models.UserInfoDto
import com.kgJr.bhandara.data.remote.apiUtils.ApiResult
import com.kgJr.bhandara.data.remote.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppRepository,
    private val locationDao: LocationDao
) : ViewModel() {

    private val _dbState = MutableStateFlow<DbStates>(DbStates.LOADING)
    val dbState: StateFlow<DbStates>
        get() = _dbState

    private val _locations = MutableStateFlow<List<LocationEntity>>(emptyList())
    val locations: StateFlow<List<LocationEntity>>
        get() = _locations

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?>
        get() = _error

    private val _showError = Channel<Boolean>()
    val showError = _showError.receiveAsFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _regUserInfo = MutableStateFlow<UserInfoDto?>(null)
    val regUserInfo: StateFlow<UserInfoDto?> get() = _regUserInfo

    private val _addedBhandara = MutableStateFlow<String?>(null)
    val addedBhandara: StateFlow<String?> get() = _addedBhandara

    private val _allBhandar = MutableStateFlow<List<BhandaraDto>?>(null)
    val allBhandar: StateFlow<List<BhandaraDto>?> get() = _allBhandar

    private val _currentBhandaras = MutableStateFlow<List<BhandaraDto>>(emptyList())
    val currentBhandaras: StateFlow<List<BhandaraDto>> get() = _currentBhandaras

    init {
        getAllLocation()
    }

    // Function to get all locations
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
            try {
                locationDao.insertLocation(location)
            } catch (exception: Exception) {
                _error.value = "Error inserting location: ${exception.message}"
            }
        }
    }


    // Function to register user
    fun registerUser(userInfo: UserInfoDto) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.registerUser(userInfo).collectLatest { response ->
                _isLoading.value = false
                when (response) {
                    is ApiResult.Success -> {
                        _regUserInfo.value = response.data
                    }
                    is ApiResult.Error -> {
                        _error.value = response.message
                    }
                }
            }
        }
    }

    // Function to add Bhandara
    fun addBhadara(bhandaraDto: BhandaraDto) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.addBhandara(bhandaraDto).collectLatest { response ->
                _isLoading.value = false
                when (response) {
                    is ApiResult.Success -> {
                        _addedBhandara.value = response.data
                    }
                    is ApiResult.Error -> {
                        _error.value = response.message
                        _showError.send(true)
                    }
                }
            }
        }
    }

    // Function to get all upcoming bhandaras
    fun getAllUpComingBhandara() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllUpComingBhandara().collectLatest { response ->
                _isLoading.value = false
                when (response) {
                    is ApiResult.Success -> {
                        _allBhandar.value = response.data
                        filterTodaysBhandara()
                    }
                    is ApiResult.Error -> {
                        _error.value = response.message
                        _showError.send(true)
                    }
                }
            }
        }
    }

    // Function to filter today's bhandaras based on date (ignoring time)
    @SuppressLint("NewApi")
    fun filterTodaysBhandara(): List<BhandaraDto>? {
        val todayDate = LocalDate.now(ZoneId.systemDefault())

        val todaysBhandaras = _allBhandar.value?.filter {
            val bhandaraDate = Instant.ofEpochMilli(it.dateOfBhandara!!)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            bhandaraDate.isEqual(todayDate) ||  it.bhandaraType!! == "everyDay"
        }

        _currentBhandaras.value = todaysBhandaras ?: emptyList()
        return todaysBhandaras
    }

    // Function to filter upcoming bhandaras (date in future)
    fun filterUpcomingBhandara(): List<BhandaraDto>? {
        val currentDate = System.currentTimeMillis()
        val upcomingBhandaras = _allBhandar.value?.filter {
            it.dateOfBhandara!! > currentDate || it.bhandaraType!! == "everyDay"
        }
        _currentBhandaras.value = upcomingBhandaras ?: emptyList()
        return upcomingBhandaras
    }

    fun filterNearMeBhandara(userLat: Double, userLon: Double): List<BhandaraDto>? {
        val maxDistanceKm = 25.0

        val nearMeBhandaras = _currentBhandaras.value.filter { bhandara ->
            val bhandaraLat = bhandara.latitude ?: return@filter false
            val bhandaraLon = bhandara.longitude ?: return@filter false

            val distance = calculateDistance(userLat, userLon, bhandaraLat, bhandaraLon)
            distance <= maxDistanceKm
        }

        _currentBhandaras.value = nearMeBhandaras
        return nearMeBhandaras
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371.0 // Radius of Earth in km

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)

        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return earthRadius * c
    }


    // Clear any temporary data or error
    fun clearData() {
        _currentBhandaras.value = emptyList()
        _addedBhandara.value = null
        _error.value = null
    }
    fun clearError(){
        _error.value = null
    }
}
