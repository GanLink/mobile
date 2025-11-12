package com.ganlink.pe.features.farmmanagement.presentation.farmadd

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganlink.pe.features.auth.data.local.dao.UserDao
import com.ganlink.pe.features.farmmanagement.data.local.dao.CreatedFarmIdDao
import com.ganlink.pe.features.farmmanagement.data.local.mappers.toCreatedFarmIdEntity
import com.ganlink.pe.features.farmmanagement.domain.models.CreateFarmRequestDto
import com.ganlink.pe.features.farmmanagement.domain.repositories.FarmMangementRepository
import com.ganlink.pe.features.farmmanagement.presentation.models.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FarmAddViewModel @Inject constructor(
    private val dao: UserDao,
    private val repository: FarmMangementRepository,
    private val createdFarmIdDao: CreatedFarmIdDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val navUserId = savedStateHandle.get<Int>("userId")?.takeIf { it > 0 }
    private val _alias = MutableStateFlow("")
    val alias: StateFlow<String> = _alias.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _ownerDni = MutableStateFlow("")
    val ownerDni: StateFlow<String> = _ownerDni.asStateFlow()

    private val _mainActivity = MutableStateFlow<MainActivity?>(null)
    val mainActivity: StateFlow<MainActivity?> = _mainActivity.asStateFlow()

    private val _aliasError = MutableStateFlow<String?>(null)
    val aliasError: StateFlow<String?> = _aliasError.asStateFlow()

    private val _dniError = MutableStateFlow<String?>(null)
    val dniError: StateFlow<String?> = _dniError.asStateFlow()

    private val _activityError = MutableStateFlow<String?>(null)
    val activityError: StateFlow<String?> = _activityError.asStateFlow()

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting.asStateFlow()

    private val _submitEvents = MutableSharedFlow<FarmAddEvent>()
    val submitEvents: SharedFlow<FarmAddEvent> = _submitEvents.asSharedFlow()

    fun setAlias(v: String) { _alias.value = v }
    fun setDescription(v: String) { _description.value = v }
    fun setOwnerDni(v: String) { _ownerDni.value = v }
    fun setMainActivity(v: MainActivity?) { _mainActivity.value = v }

    fun validate(): Boolean {
        var ok = true
        _aliasError.value = if (_alias.value.isBlank()) { ok = false; "El alias es obligatorio" } else null
        _dniError.value = when {
            _ownerDni.value.isBlank() -> {
                ok = false
                "El DNI es obligatorio"
            }
            _ownerDni.value.length != 8 || _ownerDni.value.any { !it.isDigit() } -> {
                ok = false
                "El DNI debe tener 8 dígitos numéricos"
            }
            else -> null
        }
        _activityError.value = if (_mainActivity.value == null) { ok = false; "Selecciona la actividad" } else null
        return ok
    }

    fun clear() {
        _alias.value = ""
        _description.value = ""
        _ownerDni.value = ""
        _mainActivity.value = null
        _aliasError.value = null
        _dniError.value = null
        _activityError.value = null
    }

    fun sendFarm() {
        if (!validate() || _isSubmitting.value) return

        viewModelScope.launch {
            _isSubmitting.value = true
            val userId = navUserId ?: dao.fetchAllUsers().firstOrNull()?.id
            val activityCode = _mainActivity.value?.code

            if (userId == null || activityCode == null) {
                _submitEvents.emit(FarmAddEvent.Error("No se pudo obtener los datos necesarios"))
                _isSubmitting.value = false
                return@launch
            }

            val farm = CreateFarmRequestDto(
                alias = _alias.value.trim(),
                description = _description.value.trim(),
                mainActivity = activityCode,
                ownerDni = _ownerDni.value.trim(),
                userId = userId
            )

            try {
                val response = repository.createFarm(farm)
                if (response != null) {
                    createdFarmIdDao.insert(response.toCreatedFarmIdEntity())
                    _submitEvents.emit(FarmAddEvent.Success)
                    clear()
                } else {
                    _submitEvents.emit(FarmAddEvent.Error("No se pudo registrar la granja"))
                }
            } catch (ex: Exception) {
                _submitEvents.emit(FarmAddEvent.Error(ex.localizedMessage ?: "Error inesperado"))
            } finally {
                _isSubmitting.value = false
            }
        }
    }
}

sealed class FarmAddEvent {
    object Success : FarmAddEvent()
    data class Error(val message: String) : FarmAddEvent()
}
