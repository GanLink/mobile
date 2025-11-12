package com.ganlink.pe.features.farmmanagement.data.repository

import com.ganlink.pe.features.farmmanagement.data.remote.FarmManagementService
import com.ganlink.pe.features.farmmanagement.domain.models.CreateFarmRequestDto
import com.ganlink.pe.features.farmmanagement.domain.models.CreateFarmWrapperDto
import com.ganlink.pe.features.farmmanagement.domain.models.FarmDto
import com.ganlink.pe.features.farmmanagement.domain.repositories.FarmMangementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FarmMangementRepositoryImpl @Inject constructor(private val service: FarmManagementService) : FarmMangementRepository {
    override suspend fun getFarmsByUserId(userId: Int): List<FarmDto> = withContext(Dispatchers.IO) {
        val response = service.fetchAllFarmsById(userId)
        if(response.isSuccessful){
            response.body()?.let {
                farmWrapperDto ->
                return@withContext farmWrapperDto
            }
        }


        return@withContext emptyList()
    }

    override suspend fun createFarm(farm: CreateFarmRequestDto): CreateFarmWrapperDto? =
        withContext(Dispatchers.IO) {
            val response = service.createFarm(farm)
            if (response.isSuccessful) {
                return@withContext response.body()
            }
            return@withContext null
        }

    override suspend fun deleteFarm(farmId: Int): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            service.deleteFarm(farmId).isSuccessful
        } catch (ex: Exception) {
            false
        }
    }
}
