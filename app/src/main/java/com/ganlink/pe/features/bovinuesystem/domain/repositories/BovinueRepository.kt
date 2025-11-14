package com.ganlink.pe.features.bovinuesystem.domain.repositories

import com.ganlink.pe.features.bovinuesystem.presentation.models.Bovinue
import com.ganlink.pe.features.bovinuesystem.presentation.models.BovinueMetricDraft

interface BovinueRepository {
    suspend fun getBovinueByFarmId(farmId: Int): List<Bovinue>
    suspend fun createBovinueWithMetrics(
        farmId: Int,
        metrics: List<BovinueMetricDraft>
    ): Bovinue
}
