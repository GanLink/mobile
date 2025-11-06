package com.ganlink.pe.features.farmmanagement.domain.models

data class FarmDto(
    val alias: String,
    val id: Int,
    val mainActivity: String,
    val ownerDni: String,
    val userId: Int
)