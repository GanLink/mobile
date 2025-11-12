package com.ganlink.pe.features.farmmanagement.presentation.farmspec

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganlink.pe.features.farmmanagement.data.local.dao.FarmDao
import com.ganlink.pe.features.farmmanagement.data.local.mappers.toDomain
import com.ganlink.pe.features.farmmanagement.domain.models.FarmDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FarmSpecViewModel @Inject constructor(
    private val farmDao: FarmDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val farmId = savedStateHandle.get<Int>("farmId") ?: 0

    private val _farm = MutableStateFlow<FarmDto?>(null)
    val farm: StateFlow<FarmDto?> = _farm.asStateFlow()

    init {
        viewModelScope.launch {
            farmDao.fetchById(farmId)?.let {
                _farm.value = it.toDomain()
            }
        }
    }

    fun clearSelectedFarm() {
        viewModelScope.launch {
            farmDao.deleteById(farmId)
        }
    }
}
