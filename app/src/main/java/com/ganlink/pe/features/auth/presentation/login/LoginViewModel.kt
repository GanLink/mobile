package com.ganlink.pe.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganlink.pe.core.models.User
import com.ganlink.pe.core.models.UserLogin
import com.ganlink.pe.features.auth.domain.repositories.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {
    private val _username = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    private val _user = MutableStateFlow<User?>(null)
    val username : StateFlow<String> = _username
    val password : StateFlow<String> = _password
    val user : StateFlow<User?> = _user
    fun setUsername(value: String) { _username.value = value }
    fun setPassword(value: String) { _password.value = value }


    suspend fun login(): Boolean {
        val user = withContext(Dispatchers.IO) {
            loginRepository.login(UserLogin(_username.value, _password.value))
        }
        _user.value = user
        return user != null
    }


}