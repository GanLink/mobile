package com.ganlink.pe.features.farmmanagement.domain.models

data class CreateFarmWrapperDto(
    val alias: String,
    val description: String,
    val id: Int,
    val mainActivity: String,
    val ownerDni: String,
    val userId: Int
)
