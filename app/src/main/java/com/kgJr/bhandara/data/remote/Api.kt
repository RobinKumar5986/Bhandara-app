package com.kgJr.bhandara.data.remote

import com.kgJr.bhandara.data.models.BhandaraDto
import com.kgJr.bhandara.data.models.RestResponse
import com.kgJr.bhandara.data.models.UserInfoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    companion object {
//        var BASE_URL = "http://192.168.68.73:9090"
        var BASE_URL = "https://bhandara-api-deployment-v1-latest.onrender.com"
    }

    @POST("/user/add")
    suspend fun registerUser(@Body userInfo: UserInfoDto): RestResponse<UserInfoDto>

    @POST("/api/v1/bhandara/add")
    suspend fun addBhandara(@Body bhandaraDto: BhandaraDto): RestResponse<String>

    @GET("/api/v1/bhandara/upcoming")
    suspend fun getAllUpComingBhandara(): RestResponse<List<BhandaraDto>>

    @GET("/startServer")
    suspend fun startServer(): RestResponse<Int>

}