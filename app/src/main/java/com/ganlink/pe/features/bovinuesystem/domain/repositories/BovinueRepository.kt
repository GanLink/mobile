package com.ganlink.pe.features.bovinuesystem.domain.repositories

import com.ganlink.pe.features.bovinuesystem.presentation.models.Bovinue

interface BovinueRepository {
    suspend fun getBovinueByFarmId( farmId : Int) : List<Bovinue>
}