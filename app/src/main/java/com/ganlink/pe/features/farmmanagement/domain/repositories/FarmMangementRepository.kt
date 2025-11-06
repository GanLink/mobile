package com.ganlink.pe.features.farmmanagement.domain.repositories

import com.ganlink.pe.features.farmmanagement.domain.models.FarmDto

interface FarmMangementRepository {
    suspend fun getFarmsByUserId(userId : Int) : List<FarmDto>
}