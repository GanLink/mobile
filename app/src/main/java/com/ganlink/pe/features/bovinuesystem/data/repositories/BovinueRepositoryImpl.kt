package com.ganlink.pe.features.bovinuesystem.data.repositories

import com.ganlink.pe.features.bovinuesystem.data.remote.BovinueSystemService
import com.ganlink.pe.features.bovinuesystem.domain.repositories.BovinueRepository
import com.ganlink.pe.features.bovinuesystem.presentation.models.Bovinue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BovinueRepositoryImpl @Inject constructor(private val service: BovinueSystemService) : BovinueRepository {
    override suspend fun getBovinueByFarmId(farmId : Int): List<Bovinue> = withContext(Dispatchers.IO) {
        val response = service.getBovinueByFarmId(farmId)

        if (response.isSuccessful){
            response.body()?.let {
                bovinueWrapperResponseItems ->
                return@withContext bovinueWrapperResponseItems.map {
                    bovinueWrapperResponseItem ->
                    Bovinue(
                        bovinueWrapperResponseItem.id,
                        bovinueWrapperResponseItem.farmId
                    )
                }
            }
        }

        return@withContext emptyList()
    }
}