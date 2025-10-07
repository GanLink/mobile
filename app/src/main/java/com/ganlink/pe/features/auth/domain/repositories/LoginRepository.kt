package com.ganlink.pe.features.auth.domain.repositories

import com.ganlink.pe.core.models.User
import com.ganlink.pe.core.models.UserLogin

interface LoginRepository {
    suspend fun login(user : UserLogin) : User?
}