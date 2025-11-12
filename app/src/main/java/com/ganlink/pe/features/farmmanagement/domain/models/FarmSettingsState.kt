package com.ganlink.pe.features.farmmanagement.domain.models

data class FarmSettingsState(
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false,
    val autoSyncEnabled: Boolean = true
)
