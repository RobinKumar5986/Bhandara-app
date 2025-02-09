package com.hometriangle.bhandara.ui.layouts.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hometriangle.bhandara.data.local.DbStates
import com.hometriangle.bhandara.data.local.dao.LocationDao
import com.hometriangle.bhandara.data.local.tables.LocationEntity
import com.hometriangle.bhandara.data.models.UserInfoDto
import com.hometriangle.bhandara.data.remote.apiUtils.ApiResult
import com.hometriangle.bhandara.data.remote.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppRepository,
    private val locationDao: LocationDao
) : ViewModel() {

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

    private val _showError = Channel<Boolean>()
    val showError = _showError.receiveAsFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _regUserInfo = MutableStateFlow<UserInfoDto?>(null)
    val regUserInfo: StateFlow<UserInfoDto?> get() = _regUserInfo

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
            try {
                locationDao.insertLocation(location)
            } catch (exception: Exception) {
                _error.value = "Error inserting location: ${exception.message}"
            }
        }
    }
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
                        _showError.send(true)
                    }
                }
            }
        }
    }

}