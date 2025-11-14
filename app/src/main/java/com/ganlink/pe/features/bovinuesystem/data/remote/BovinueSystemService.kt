package com.ganlink.pe.features.bovinuesystem.data.remote

import com.ganlink.pe.features.bovinuesystem.domain.models.BovinueMetricResponseDTO
import com.ganlink.pe.features.bovinuesystem.domain.models.BovinueMetricWrapperDTO
import com.ganlink.pe.features.bovinuesystem.presentation.models.Bovinue
import com.ganlink.pe.features.bovinuesystem.presentation.models.BovinueWrapperResponse
import com.ganlink.pe.features.bovinuesystem.presentation.models.CreateBovinueDTO
import com.ganlink.pe.features.bovinuesystem.presentation.models.CreateBovinueMetric
import com.ganlink.pe.features.bovinuesystem.presentation.models.UpdateBovinueMetric
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BovinueSystemService {
    @GET("api/v1/Bovinue/farm/{farmId}")
    suspend fun getBovinueByFarmId(@Path("farmId") farmId : Int) : Response<BovinueWrapperResponse>
    @Headers("Content-type: application/json")
    @POST("api/v1/BovinueMetric")
    suspend fun createBovinueMetric(@Body metric : CreateBovinueMetric) : Response<BovinueMetricResponseDTO>
    @Headers("Content-type: application/json")
    @PUT("api/v1/BovinueMetric/{metricId}")
    suspend fun updateBovinueMetric(
        @Path("metricId") metricId: Int,
        @Body metric: UpdateBovinueMetric
    ) : Response<BovinueMetricResponseDTO>
    @Headers("Content-type: application/json")
    @POST("api/v1/bovinue")
    suspend fun createBovinue(@Body bovinue: CreateBovinueDTO) : Response<Bovinue>
    @GET("api/v1/BovinueMetric/bovinue/{bovinueId}")
    suspend fun getMetricByBovinueId(@Path("bovinueId") bovinueId : Int) : Response<BovinueMetricWrapperDTO>
}
