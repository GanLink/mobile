package com.ganlink.pe.features.auth.domain.repositories

import com.ganlink.pe.core.models.UserRegister

interface RegisterRepository {
    suspend fun sendRegister(user : UserRegister)
}