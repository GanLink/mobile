package com.ganlink.pe.features.bovinuesystem.domain.models

data class BovinueMetricResponseDTO(
    val bovinueId: Int,
    val bovinueMPId: Int,
    val categoryName: String,
    val createdDate: String,
    val date: String,
    val id: Int,
    val parameterName: String,
    val quantity: Int,
    val updatedDate: String
)