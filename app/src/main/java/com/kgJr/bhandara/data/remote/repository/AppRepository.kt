package com.kgJr.bhandara.data.remote.repository

import com.kgJr.bhandara.data.models.BhandaraDto
import com.kgJr.bhandara.data.models.UserInfoDto
import com.kgJr.bhandara.data.remote.apiUtils.ApiResult
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun registerUser(userInfo: UserInfoDto):  Flow<ApiResult<UserInfoDto>>
    suspend fun addBhandara(bhandaraDto: BhandaraDto):  Flow<ApiResult<String>>
    suspend fun getAllUpComingBhandara(): Flow<ApiResult<List<BhandaraDto>>>
    suspend fun startServer(): Flow<ApiResult<Int>>
}