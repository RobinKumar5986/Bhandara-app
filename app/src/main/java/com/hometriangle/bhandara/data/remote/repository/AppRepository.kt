package com.hometriangle.bhandara.data.remote.repository

import com.hometriangle.bhandara.data.models.BhandaraDto
import com.hometriangle.bhandara.data.models.UserInfoDto
import com.hometriangle.bhandara.data.remote.apiUtils.ApiResult
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun registerUser(userInfo: UserInfoDto):  Flow<ApiResult<UserInfoDto>>
    suspend fun addBhandara(bhandaraDto: BhandaraDto):  Flow<ApiResult<String>>
}