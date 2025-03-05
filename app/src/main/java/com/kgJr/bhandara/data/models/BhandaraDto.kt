package com.kgJr.bhandara.data.models

import kotlinx.serialization.Serializable

@Serializable
data class BhandaraDto(
    val id: Long?,
    val name: String?,
    val description: String?,
    val latitude: Double?,
    val longitude: Double?,
    val createdOn: String?,
    val updatedOn: String?,
    val dateOfBhandara: Long?,
    val startingTime: Double?,
    val endingTime: Double?,
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