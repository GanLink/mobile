package com.ganlink.pe.features.auth.data.remote.services

import com.ganlink.pe.core.models.UserLogin
import com.ganlink.pe.features.auth.domain.models.UserLoginDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @Headers("Content-Type: application/json")
    @POST("api/v1/Authorization/sign-in")
    suspend fun login(@Body user : UserLogin) : Response<UserLoginDto>
}