package com.ganlink.pe.features.auth.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.ganlink.pe.R
import com.ganlink.pe.core.ui.components.CustomSpacerVertical
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun Login(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onRegister: () -> Unit,
    onLogin: (Int, String) -> Unit
) {
    val username by loginViewModel.username.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val user by loginViewModel.user.collectAsState()

    val scope = rememberCoroutineScope()
    val isVisible = remember { mutableStateOf(false) }
    val showError = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp, bottom = 44.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .clip(shape = RoundedCornerShape(120.dp))
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.image1),
                    contentDescription = null
                )
            }
        }

        Column(modifier = Modifier.width(280.dp)) {
            OutlinedTextField(
                onValueChange = { loginViewModel.setUsername(it) },
                value = username,
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                },
                placeholder = { Text("Exp : Pedrito") },
                label = { Text("Username") }
            )

            CustomSpacerVertical()

            OutlinedTextField(
                onValueChange = { loginViewModel.setPassword(it) },
                value = password,
                trailingIcon = {
                    IconButton(onClick = { isVisible.value = !isVisible.value }) {
                        if (isVisible.value) {
                            Icon(imageVector = Icons.Default.Visibility, contentDescription = null)
                        } else {
                            Icon(imageVector = Icons.Default.VisibilityOff, contentDescription = null)
                        }
                    }
                },
                placeholder = { Text("Exp : Password") },
                label = { Text("Password") },
                visualTransformation = if (isVisible.value) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )

            Text(
                text = "Don't have an Account? Register",
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable { onRegister() }
            )
        }

        CustomSpacerVertical()

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    scope.launch {
                        val ok = loginViewModel.login()
                        if(user == null){
                            showError.value = true
                            return@launch
                        }
                        if (ok) onLogin(user!!.id, user!!.username) else showError.value = true
                    }
                },
                modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
            ) {
                Text("Ok")
            }
        }

        if (showError.value) {
            CustomSpacerVertical()
            Text(
                text = "Login failed. Please check your credentials.",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}