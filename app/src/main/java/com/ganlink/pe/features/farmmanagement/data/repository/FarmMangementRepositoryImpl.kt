package com.ganlink.pe.features.farmmanagement.data.repository

import com.ganlink.pe.features.farmmanagement.data.remote.FarmManagementService
import com.ganlink.pe.features.farmmanagement.domain.models.FarmDto
import com.ganlink.pe.features.farmmanagement.domain.models.FarmsWrapperDto
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

}