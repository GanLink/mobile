package com.ganlink.pe.features.farmmanagement.presentation.farmspec

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganlink.pe.features.bovinuesystem.domain.repositories.BovinueRepository
import com.ganlink.pe.features.bovinuesystem.presentation.models.Bovinue
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
    private val bovinueRepository: BovinueRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val farmId = savedStateHandle.get<Int>("farmId") ?: 0

    private val _farm = MutableStateFlow<FarmDto?>(null)
    val farm: StateFlow<FarmDto?> = _farm.asStateFlow()

    private val _bovinues = MutableStateFlow<List<Bovinue>>(emptyList())
    val bovinues: StateFlow<List<Bovinue>> = _bovinues.asStateFlow()

    init {
        viewModelScope.launch {
            farmDao.fetchById(farmId)?.let {
                _farm.value = it.toDomain()
            }
        }

        fetchBovinues()
    }

    fun clearSelectedFarm() {
        viewModelScope.launch {
            farmDao.deleteById(farmId)
        }
    }

    private fun fetchBovinues() {
        viewModelScope.launch {
            runCatching {
                bovinueRepository.getBovinueByFarmId(farmId)
            }.onSuccess {
                _bovinues.value = it
            }.onFailure {
                _bovinues.value = emptyList()
            }
        }
    }
}
