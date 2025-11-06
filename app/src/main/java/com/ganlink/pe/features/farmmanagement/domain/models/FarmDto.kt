package com.ganlink.pe.features.farmmanagement.domain.models

data class FarmsWrapperDtoItem(
    val alias: String,
    val id: Int,
    val mainActivity: String,
    val ownerDni: String,
    val userId: Int
)