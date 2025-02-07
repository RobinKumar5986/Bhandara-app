package com.hometriangle.bhandara.data.remote.repository

import com.hometriangle.bhandara.data.remote.apiUtils.ApiResult
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getUsers(): Flow<ApiResult<String>>
}