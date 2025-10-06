package com.ganlink.pe.features.auth.presentation.register

import androidx.lifecycle.ViewModel
import com.ganlink.pe.features.auth.domain.repositories.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(registerRepository: RegisterRepository) : ViewModel() {
    private val _username = MutableStateFlow("")
    private val _firstName = MutableStateFlow("")
    private val _lastName = MutableStateFlow("")
    private val _ruc = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _passwordConfirmation = MutableStateFlow("")
    val username : StateFlow<String> = _username
    val firstName : StateFlow<String> = _firstName
    val lastName : StateFlow<String> = _lastName
    val ruc : StateFlow<String> = _ruc
    val password : StateFlow<String> = _password
    val passwordConfirmation : StateFlow<String> = _passwordConfirmation
    fun setUsername(username: String) {
        _username.value = username
    }

    fun setFirstName(firstname: String) {
        _firstName.value = firstname
    }

    fun setLastName(lastname: String) {
        _lastName.value = lastname
    }

    fun setRuc(ruc: String) {
        _ruc.value = ruc
    }

    fun setPassword(password: String) {
        _password.value = password
    }
    fun setPasswordConfirmation(passwordconf: String) {
        _passwordConfirmation.value = passwordconf
    }
}