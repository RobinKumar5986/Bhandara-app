package com.hometriangle.bhandara.data.remote

import retrofit2.http.GET

interface Api {
    companion object {
        var BASE_URL = "https://reqres.in"
    }

    @GET("/")
    suspend fun getUserUser(): String
}