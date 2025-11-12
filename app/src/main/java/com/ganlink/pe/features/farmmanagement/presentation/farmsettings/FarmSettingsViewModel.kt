package com.ganlink.pe.features.farmmanagement.presentation.farmsettings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganlink.pe.features.farmmanagement.data.local.preferences.FarmSettingsRepository
import com.ganlink.pe.features.farmmanagement.domain.models.FarmSettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class FarmSettingsViewModel @Inject constructor(
    private val repository: FarmSettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FarmSettingsState())
    val state: StateFlow<FarmSettingsState> = _state

    init {
        viewModelScope.launch {
            repository.settings.collectLatest { settings ->
                _state.value = settings
                applyDarkMode(settings.darkModeEnabled)
            }
        }
    }

    fun setNotifications(enabled: Boolean) {
        viewModelScope.launch {
            repository.updateNotifications(enabled)
        }
    }

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            repository.updateDarkMode(enabled)
            applyDarkMode(enabled)
        }
    }

    fun setAutoSync(enabled: Boolean) {
        viewModelScope.launch {
            repository.updateAutoSync(enabled)
        }
    }

    fun resetDefaults() {
        viewModelScope.launch {
            repository.resetDefaults()
        }
    }

    fun applyCurrentSettings() {
        applyDarkMode(_state.value.darkModeEnabled)
    }

    private fun applyDarkMode(enabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (enabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}
