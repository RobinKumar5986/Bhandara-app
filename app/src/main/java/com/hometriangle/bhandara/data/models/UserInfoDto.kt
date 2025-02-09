package com.hometriangle.bhandara.data.models

data class UserInfoDto(
    val id: Long?,
    val phoneNo: String?,
    val userUid: String?,
    val latitude: Double?,
    val longitude: Double?,
    val city: String?,
    val state: String?,
    val country: String?,
    val createdOn: String?,
    val updatedOn: String?
)