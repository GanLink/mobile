package com.ganlink.pe.features.bovinuesystem.presentation.models

data class UpdateBovinueMetric(
    val id: Int,
    val bovinueId: Int,
    val bovinueMPId: Int,
    val date: String,
    val quantity: Int
)
