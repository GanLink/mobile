package com.ganlink.pe.features.bovinuesystem.domain.repositories

import com.ganlink.pe.features.bovinuesystem.domain.models.BovinueMetricDto
import com.ganlink.pe.features.bovinuesystem.presentation.models.Bovinue
import com.ganlink.pe.features.bovinuesystem.presentation.models.BovinueMetricDraft
import com.ganlink.pe.features.bovinuesystem.presentation.models.UpdateBovinueMetric

interface BovinueRepository {
    suspend fun getBovinueByFarmId(farmId: Int): List<Bovinue>
    suspend fun createBovinueWithMetrics(
        farmId: Int,
        metrics: List<BovinueMetricDraft>
    ): Bovinue
    suspend fun getMetricByBovinueId(bovinueId : Int) : List<BovinueMetricDto>
    suspend fun updateBovinueMetric(metric: UpdateBovinueMetric): BovinueMetricDto
}
