package com.ganlink.pe.features.farmmanagement.domain.repositories

import com.ganlink.pe.features.farmmanagement.domain.models.CreateFarmRequestDto
import com.ganlink.pe.features.farmmanagement.domain.models.CreateFarmWrapperDto
import com.ganlink.pe.features.farmmanagement.domain.models.FarmDto

interface FarmMangementRepository {
    suspend fun getFarmsByUserId(userId : Int) : List<FarmDto>
    suspend fun createFarm(farm : CreateFarmRequestDto) : CreateFarmWrapperDto?
    suspend fun deleteFarm(farmId: Int): Boolean
}
