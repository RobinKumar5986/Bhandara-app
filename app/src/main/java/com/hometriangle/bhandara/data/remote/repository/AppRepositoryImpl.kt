package com.hometriangle.bhandara.data.remote.repository

import coil.network.HttpException
import com.hometriangle.bhandara.data.remote.Api
import com.hometriangle.bhandara.data.remote.apiUtils.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class AppRepositoryImpl(
    val api: Api
): AppRepository {
    override suspend fun getUsers(): Flow<ApiResult<String>> {
        return flow {
            val userFromApi = try {
                api.getUserUser()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "IO Error: Error parsing the data"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "HTTP Error: Error While Networking"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "Error: Something went wrong"))
                return@flow
            }
            emit(ApiResult.Success(data = userFromApi))
        }
    }
}