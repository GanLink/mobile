package com.ganlink.pe.features.bovinuesystem.data.repositories

import com.ganlink.pe.features.bovinuesystem.data.remote.BovinueSystemService
import com.ganlink.pe.features.bovinuesystem.domain.repositories.BovinueRepository
import com.ganlink.pe.features.bovinuesystem.presentation.models.Bovinue
import com.ganlink.pe.features.bovinuesystem.presentation.models.BovinueMetricDraft
import com.ganlink.pe.features.bovinuesystem.presentation.models.CreateBovinueDTO
import com.ganlink.pe.features.bovinuesystem.presentation.models.CreateBovinueMetric
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class BovinueRepositoryImpl @Inject constructor(
    private val service: BovinueSystemService
) : BovinueRepository {
    override suspend fun getBovinueByFarmId(farmId: Int): List<Bovinue> = withContext(Dispatchers.IO) {
        val response = service.getBovinueByFarmId(farmId)

        if (response.isSuccessful) {
            response.body()?.let { bovinueWrapperResponseItems ->
                return@withContext bovinueWrapperResponseItems.map { bovinueWrapperResponseItem ->
                    Bovinue(
                        bovinueWrapperResponseItem.id,
                        bovinueWrapperResponseItem.farmId
                    )
                }
            }
        }

        return@withContext emptyList()
    }

    override suspend fun createBovinueWithMetrics(
        farmId: Int,
        metrics: List<BovinueMetricDraft>
    ): Bovinue = withContext(Dispatchers.IO) {
        val bovinueResponse = service.createBovinue(
            CreateBovinueDTO(farmId.toString())
        )

        if (!bovinueResponse.isSuccessful) {
            throw HttpException(bovinueResponse)
        }

        val createdBovinue = bovinueResponse.body()
            ?: error("El servicio no retornó información del bovinue creado")

        metrics.forEach { draft ->
            val metricResponse = service.createBovinueMetric(
                CreateBovinueMetric(
                    bovinueId = createdBovinue.id,
                    bovinueMPId = draft.bovinueMPId,
                    date = draft.date,
                    quantity = draft.quantity
                )
            )

            if (!metricResponse.isSuccessful) {
                throw HttpException(metricResponse)
            }
        }

        return@withContext createdBovinue
    }
}
