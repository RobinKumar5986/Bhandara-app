package com.hometriangle.bhandara.data.remote

import com.hometriangle.bhandara.data.models.BhandaraDto
import com.hometriangle.bhandara.data.models.RestResponse
import com.hometriangle.bhandara.data.models.UserInfoDto
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    companion object {
        var BASE_URL = "http://192.168.68.86:9090"
    }

    @POST("/user/add")
    suspend fun registerUser(@Body userInfo: UserInfoDto): RestResponse<UserInfoDto>

    @POST("/api/v1/bhandara/add")
    suspend fun addBhandara(@Body bhandaraDto: BhandaraDto): RestResponse<String>

}