package com.ganlink.pe.core.models

data class UserRegister(
    val username : String,
    val firstName : String,
    val lastName : String,
    val email : String,
    val ruc : String,
    val password : String
)