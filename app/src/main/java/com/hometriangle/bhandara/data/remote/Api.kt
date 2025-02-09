package com.hometriangle.bhandara.data.remote

import com.hometriangle.bhandara.data.models.RestResponse
import com.hometriangle.bhandara.data.models.UserInfoDto
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    companion object {
        var BASE_URL = "http://192.168.68.59:9090"
    }

    @POST("/user/add")
    suspend fun registerUser(@Body userInfo: UserInfoDto): RestResponse<UserInfoDto>
}