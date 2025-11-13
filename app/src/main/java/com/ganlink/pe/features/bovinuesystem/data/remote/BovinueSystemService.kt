package com.ganlink.pe.features.bovinuesystem.data.remote

import com.ganlink.pe.features.bovinuesystem.presentation.models.BovinueWrapperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BovinueSystemService {
    @GET("api/v1/Bovinue/farm/{farmId}")
    suspend fun getBovinueByFarmId(@Path("farmId") farmId : Int) : Response<BovinueWrapperResponse>
}