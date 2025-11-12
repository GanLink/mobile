package com.ganlink.pe.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import com.ganlink.pe.core.models.User
import com.ganlink.pe.core.models.UserLogin
import com.ganlink.pe.features.auth.data.local.dao.UserDao
import com.ganlink.pe.features.auth.data.local.models.UserEntity
import com.ganlink.pe.features.auth.domain.repositories.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userDao: UserDao
) : ViewModel() {
    private val _username = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    private val _user = MutableStateFlow<User?>(null)
    private val _isLoading = MutableStateFlow(false)
    val username : StateFlow<String> = _username
    val password : StateFlow<String> = _password
    val user : StateFlow<User?> = _user
    val isLoading: StateFlow<Boolean> = _isLoading
    fun setUsername(value: String) { _username.value = value }
    fun setPassword(value: String) { _password.value = value }


    suspend fun login(): User? {
        if (_isLoading.value) return _user.value

        _isLoading.value = true
        val user = try {
            withContext(Dispatchers.IO) {
                loginRepository.login(UserLogin(_username.value.trim(), _password.value))
            }
        } catch (ex: Exception) {
            null
        } finally {
            _isLoading.value = false
        }

        if (user != null) {
            try {
                withContext(Dispatchers.IO) {
                    userDao.insert(UserEntity(user.id))
                }
            } catch (_: Exception) {
                // Allow login to continue even if caching fails
            }
        }

        _user.value = user
        return user
    }


}
