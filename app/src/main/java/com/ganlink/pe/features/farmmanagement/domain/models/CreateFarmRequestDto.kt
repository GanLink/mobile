package com.ganlink.pe.features.farmmanagement.domain.models

data class CreateFarmRequestDto(
    val alias: String,
    val description: String,
    val mainActivity: Int,
    val ownerDni: String,
    val userId: Int
)
