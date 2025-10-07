package com.ganlink.pe.features.auth.domain.models

data class UserLoginDto(
    val id: Int,
    val token: String,
    val username: String
)