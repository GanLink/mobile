package com.ganlink.pe.features.auth.data.remote.services

import com.ganlink.pe.core.models.UserRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RegisterService {
    @Headers("Content-Type: application/json")
    @POST("api/v1/Authorization/sign-up")
    suspend fun registration(@Body userRegister: UserRegister)
}