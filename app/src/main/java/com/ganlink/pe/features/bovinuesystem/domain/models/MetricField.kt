package com.ganlink.pe.features.bovinuesystem.presentation.models

import androidx.compose.ui.text.input.KeyboardType

data class MetricField(
    val title: String,
    val placeholder: String,
    val keyboardType: KeyboardType,
    val checked: Boolean = false,
    val value: String = ""
)
