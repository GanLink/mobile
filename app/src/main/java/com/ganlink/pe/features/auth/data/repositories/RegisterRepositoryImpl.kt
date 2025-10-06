package com.ganlink.pe.features.auth.data.repositories

import com.ganlink.pe.core.models.UserRegister
import com.ganlink.pe.features.auth.domain.repositories.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterRepositoryImpl : RegisterRepository {
    override suspend fun sendRegister(user: UserRegister) = withContext(Dispatchers.IO) {

    }
}