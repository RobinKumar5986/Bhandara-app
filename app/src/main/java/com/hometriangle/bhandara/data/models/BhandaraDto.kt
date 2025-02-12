package com.hometriangle.bhandara.data.models

import java.util.Date

data class BhandaraDto(
    val id: Long?,
    val name: String?,
    val description: String?,
    val latitude: Double?,
    val longitude: Double?,
    val createdOn: String?,
    val updatedOn: String?,
    val dateOfBhandara: String?,
    val startingTime: String?,
    val endingTime: String?,
    val verificationType: String?,
    val foodType: String?,
    val organizationType: String?,
    val organizationName: String?,
    val phoneNumber: String?,
    val needVolunteer: Boolean?,
    val contactForVolunteer: String?,
    val specialNote: String?,
    val createdBy: String?,
    val image: String?,
    val bhandaraType: String?
)