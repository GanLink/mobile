package com.ganlink.pe.features.farmmanagement.presentation.farmspec

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganlink.pe.features.auth.data.local.dao.UserDao
import com.ganlink.pe.features.farmmanagement.presentation.models.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FarmAddViewModel @Inject constructor(
    private val dao: UserDao
) : ViewModel() {
    private val _alias = MutableStateFlow("")
    val alias: StateFlow<String> = _alias.asStateFlow()

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

    fun setAlias(v: String) { _alias.value = v }
    fun setOwnerDni(v: String) { _ownerDni.value = v }
    fun setMainActivity(v: MainActivity?) { _mainActivity.value = v }

    fun validate(): Boolean {
        var ok = true
        _aliasError.value = if (_alias.value.isBlank()) { ok = false; "El alias es obligatorio" } else null
        _dniError.value = if (_ownerDni.value.isBlank()) { ok = false; "El DNI es obligatorio" } else null
        _activityError.value = if (_mainActivity.value == null) { ok = false; "Selecciona la actividad" } else null
        return ok
    }

    fun clear() {
        _alias.value = ""
        _ownerDni.value = ""
        _mainActivity.value = null
        _aliasError.value = null
        _dniError.value = null
        _activityError.value = null
    }

    fun getUserId(){
        viewModelScope.launch {
            val userDao = dao.fetchAllUsers()
        }
    }
}