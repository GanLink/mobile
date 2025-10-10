package com.ganlink.pe.features.farmmanagement.domain.models

data class CreateFarmDto(
    val alias: String,
    val mainActivity: Int,
    val ownerDni: String,
    val userId: Int
)