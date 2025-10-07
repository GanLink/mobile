package com.ganlink.pe.features.farmhome.domain.models

data class CreateFarmDto(
    val alias: String,
    val mainActivity: Int,
    val ownerDni: String,
    val userId: Int
)