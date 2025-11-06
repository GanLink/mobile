package com.ganlink.pe.features.farmmanagement.data.remote

import com.ganlink.pe.features.farmmanagement.domain.models.FarmsWrapperDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FarmManagementService {
    @GET("api/v1/Farm/users/{userId}/farms")
    suspend fun fetchAllFarmsById(@Path("userId") userId : Int) : Response<FarmsWrapperDto>
}