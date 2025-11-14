package com.ganlink.pe.features.bovinuesystem.presentation.models

data class CreateBovinueMetric(
    val bovinueId: Int,
    val bovinueMPId: Int,
    val date: String,
    val quantity: Int
)