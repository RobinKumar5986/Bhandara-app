package com.hometriangle.bhandara.data.remote.repository

import coil.network.HttpException
import com.hometriangle.bhandara.data.models.BhandaraDto
import com.hometriangle.bhandara.data.models.RestStatus
import com.hometriangle.bhandara.data.models.UserInfoDto
import com.hometriangle.bhandara.data.remote.Api
import com.hometriangle.bhandara.data.remote.apiUtils.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class AppRepositoryImpl(
    private val api: Api
): AppRepository {
    override suspend fun registerUser(userInfo: UserInfoDto): Flow<ApiResult<UserInfoDto>> {
        return flow {
            val response = try{
                api.registerUser(userInfo)
            }catch (e: IOException){
                e.printStackTrace()
                emit(ApiResult.Error(message =  "IO ERROR: ${e.message}"))
                return@flow
            }catch (e: HttpException){
                e.printStackTrace()
                emit(ApiResult.Error(message =  "NETWORKING ERROR: ${e.message}"))
                return@flow
            }catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "ERROR: ${e.message}"))
                return@flow
            }
            if(response.status == RestStatus.SUCCESS){
                emit(ApiResult.Success(response.data ?: return@flow emit(ApiResult.Error("Data is null"))))
            }else{
                emit(ApiResult.Error(message = "API ERROR: ${response.message}"))
            }
        }
    }

    override suspend fun addBhandara(bhandaraDto: BhandaraDto): Flow<ApiResult<String>> {
        return flow {
            val response = try{
                api.addBhandara(bhandaraDto)
            }catch (e: IOException){
                e.printStackTrace()
                emit(ApiResult.Error(message =  "IO ERROR: ${e.message}"))
                return@flow
            }catch (e: HttpException){
                e.printStackTrace()
                emit(ApiResult.Error(message =  "NETWORKING ERROR: ${e.message}"))
                return@flow
            }catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "ERROR: ${e.message}"))
                return@flow
            }
            if(response.status == RestStatus.SUCCESS){
                emit(ApiResult.Success(response.data ?: return@flow emit(ApiResult.Error("Data is null"))))
            }else{
                emit(ApiResult.Error(message = "API ERROR: ${response.message}"))
            }
        }
    }
}