package com.ganlink.pe.features.farmmanagement.presentation.farmhome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganlink.pe.core.resources.resourceToEntity
import com.ganlink.pe.features.auth.data.local.dao.UserDao
import com.ganlink.pe.features.farmmanagement.data.local.dao.FarmDao
import com.ganlink.pe.features.farmmanagement.data.local.mappers.toEntity
import com.ganlink.pe.features.farmmanagement.domain.models.FarmDto
import com.ganlink.pe.features.farmmanagement.domain.models.UserResource
import com.ganlink.pe.features.farmmanagement.domain.repositories.FarmMangementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FarmHomeViewModel @Inject constructor(
    private val repository : FarmMangementRepository,
    private val dao: UserDao,
    private val farmDao: FarmDao
) : ViewModel() {
    private val _username = MutableStateFlow("")
    val username : StateFlow<String> = _username
    var userId = 0
    private val _farms = MutableStateFlow<List<FarmDto>>(emptyList())
    val farms : StateFlow<List<FarmDto>> = _farms

    fun getFarmsById(userId : Int){
        viewModelScope.launch {
            _farms.value = repository.getFarmsByUserId(userId)
        }
        this.userId = userId
        val userResource = UserResource(userId)
        viewModelScope.launch {
            val userDao = dao.fetchUserById(userId)
            if (userDao.isEmpty()) {
                dao.insert(resourceToEntity(userResource))
            }
        }
    }

    fun selectFarm(farm: FarmDto) {
        viewModelScope.launch {
            farmDao.insert(farm.toEntity())
        }
    }

    fun deleteFarm(farmId: Int) {
        viewModelScope.launch {
            val success = repository.deleteFarm(farmId)
            if (success) {
                _farms.value = _farms.value.filterNot { it.id == farmId }
                farmDao.deleteById(farmId)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dao.clearAll()
            farmDao.clearAll()
            _farms.value = emptyList()
            _username.value = ""
            userId = 0
        }
    }
}
