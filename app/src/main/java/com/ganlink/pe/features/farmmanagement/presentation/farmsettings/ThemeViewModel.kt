package com.ganlink.pe.features.farmmanagement.presentation.farmsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganlink.pe.features.farmmanagement.data.local.preferences.FarmSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ThemeViewModel @Inject constructor(
    repository: FarmSettingsRepository
) : ViewModel() {
    val darkTheme: StateFlow<Boolean> = repository.settings
        .map { it.darkModeEnabled }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )
}
