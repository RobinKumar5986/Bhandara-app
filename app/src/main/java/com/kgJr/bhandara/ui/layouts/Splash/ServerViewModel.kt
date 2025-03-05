package com.kgJr.bhandara.ui.layouts.Splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kgJr.bhandara.data.remote.apiUtils.ApiResult
import com.kgJr.bhandara.data.remote.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServerViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _responseMsg = MutableStateFlow<Int?>(null)
    val responseMsg: StateFlow<Int?> get() = _responseMsg

    private val _errMsg = MutableStateFlow<String?>(null)
    val errMsg: StateFlow<String?> get() = _errMsg

    fun startServer(retryCount: Int = 4, delayMillis: Long = 2000) {
        viewModelScope.launch {
            var attempt = 0
            while (attempt < retryCount) {
                repository.startServer().collectLatest { response ->
                    when (response) {
                        is ApiResult.Success -> {
                            _responseMsg.value = response.data
                            return@collectLatest
                        }
                        is ApiResult.Error -> {
                            if (attempt == retryCount - 1 || !isTimeoutError(response.message ?: "")) {
                                _errMsg.value = response.message
                            }
                            if (attempt != retryCount - 1 && isTimeoutError(response.message ?: "")) {
                                delay(delayMillis * (attempt + 1))
                            }
                        }
                    }
                }
                attempt++
            }
        }
    }

    // Helper function to check if the error message contains "timeout"
    private fun isTimeoutError(error: String): Boolean {
        return error.contains("timeout", ignoreCase = true)
    }
}
