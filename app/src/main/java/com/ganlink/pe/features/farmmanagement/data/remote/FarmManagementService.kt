package com.ganlink.pe.features.farmmanagement.data.remote

import com.ganlink.pe.features.farmmanagement.domain.models.CreateFarmRequestDto
import com.ganlink.pe.features.farmmanagement.domain.models.CreateFarmWrapperDto
import com.ganlink.pe.features.farmmanagement.domain.models.FarmDto
import com.ganlink.pe.features.farmmanagement.domain.models.FarmsWrapperDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface FarmManagementService {
    @GET("api/v1/Farm/users/{userId}/farms")
    suspend fun fetchAllFarmsById(@Path("userId") userId : Int) : Response<FarmsWrapperDto>
    @Headers("Content-type: application/json")
    @POST("api/v1/Farm")
    suspend fun createFarm(@Body farm : CreateFarmRequestDto) : Response<CreateFarmWrapperDto>

    @DELETE("api/v1/Farm/{farmId}")
    suspend fun deleteFarm(@Path("farmId") farmId: Int): Response<Unit>
}
