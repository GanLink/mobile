package com.ganlink.pe.features.auth.data.repositories

import com.ganlink.pe.core.models.User
import com.ganlink.pe.core.models.UserLogin
import com.ganlink.pe.features.auth.data.remote.services.LoginService
import com.ganlink.pe.features.auth.domain.repositories.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginService: LoginService) : LoginRepository {
    override suspend fun login(user: UserLogin): User? = withContext(Dispatchers.IO) {

        val response = loginService.login(user)

        if (response.isSuccessful){
            response.body()?.let { userLoginDto ->
                return@withContext User(
                    id = userLoginDto.id,
                    token = userLoginDto.token,
                    username = userLoginDto.username
                )
            }
        }

        return@withContext null
    }
}