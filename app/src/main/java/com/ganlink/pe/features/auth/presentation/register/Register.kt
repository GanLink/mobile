package com.ganlink.pe.features.auth.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DriveFileRenameOutline
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockPerson
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ganlink.pe.R
import com.ganlink.pe.core.ui.components.CustomSpacer
import com.ganlink.pe.core.ui.components.CustomSpacerVertical

@Composable
fun Register(
    registerViewModel: RegisterViewModel = hiltViewModel(),
    onRegister: () -> Unit
) {
    val username by registerViewModel.username.collectAsState()
    val firstName by registerViewModel.firstName.collectAsState()
    val lastName by registerViewModel.lastName.collectAsState()
    val ruc by registerViewModel.ruc.collectAsState()
    val email by registerViewModel.email.collectAsState() // ← nuevo campo
    val password by registerViewModel.password.collectAsState()
    val passwordConf by registerViewModel.passwordConfirmation.collectAsState()

    var showErrors by remember { mutableStateOf(false) }

    val isUsernameError = username.length > 10 || username.isBlank()
    val isFirstNameError = firstName.length > 10 || firstName.isBlank()
    val isLastNameError = lastName.length > 20 || lastName.isBlank()
    val isRucError = ruc.isBlank() ||
            ruc.length != 11 ||
            !ruc.all { it.isDigit() } ||
            !(ruc.startsWith("10") || ruc.startsWith("20"))
    val isEmailError = email.isBlank() ||
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordError = password.length > 20 || password.isBlank()
    val isPasswordConfError = passwordConf != password || passwordConf.isBlank()

    val allValid = !isUsernameError && !isFirstNameError && !isLastNameError &&
            !isRucError && !isEmailError && !isPasswordError && !isPasswordConfError

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 50.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(200.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .size(width = 150.dp, height = 50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Register",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Image(
            painter = painterResource(R.drawable.image1),
            contentDescription = null,
            modifier = Modifier.size(160.dp)
        )
        Column(
            modifier = Modifier.width(380.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { registerViewModel.setUsername(it) },
                placeholder = { Text("Expl: Destroyer2009") },
                label = { Text("Username") },
                leadingIcon = { Icon(Icons.Default.Man, null) },
                isError = showErrors && isUsernameError,
                modifier = Modifier.fillMaxWidth()
            )
            if (showErrors && isUsernameError) {
                Text(
                    "Máx. 10 caracteres",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            OutlinedTextField(
                value = firstName,
                onValueChange = { registerViewModel.setFirstName(it) },
                placeholder = { Text("Expl: Pepe") },
                label = { Text("First Name") },
                leadingIcon = { Icon(Icons.Default.DriveFileRenameOutline, null) },
                isError = showErrors && isFirstNameError,
                modifier = Modifier.fillMaxWidth()
            )
            if (showErrors && isFirstNameError) {
                Text(
                    "Máx. 10 caracteres",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            OutlinedTextField(
                value = lastName,
                onValueChange = { registerViewModel.setLastName(it) },
                placeholder = { Text("Expl: McCartney") },
                label = { Text("Last Name") },
                leadingIcon = { Icon(Icons.Default.PersonAdd, null) },
                isError = showErrors && isLastNameError,
                modifier = Modifier.fillMaxWidth()
            )
            if (showErrors && isLastNameError) {
                Text(
                    "Máx. 20 caracteres",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            OutlinedTextField(
                value = ruc,
                onValueChange = { registerViewModel.setRuc(it) },
                placeholder = { Text("Expl: 10123456789") },
                label = { Text("RUC") },
                leadingIcon = { Icon(Icons.Default.FileCopy, null) },
                isError = showErrors && isRucError,
                modifier = Modifier.fillMaxWidth()
            )
            if (showErrors && isRucError) {
                Text(
                    "Debe iniciar con 10 o 20, tener 11 dígitos y solo números",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            OutlinedTextField(
                value = email,
                onValueChange = { registerViewModel.setEmail(it) },
                placeholder = { Text("Expl: example@mail.com") },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, null) },
                isError = showErrors && isEmailError,
                modifier = Modifier.fillMaxWidth()
            )
            if (showErrors && isEmailError) {
                Text(
                    "Formato de correo inválido",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            OutlinedTextField(
                value = password,
                onValueChange = { registerViewModel.setPassword(it) },
                placeholder = { Text("Expl: Password123") },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, null) },
                visualTransformation = PasswordVisualTransformation(),
                isError = showErrors && isPasswordError,
                modifier = Modifier.fillMaxWidth()
            )
            if (showErrors && isPasswordError) {
                Text(
                    "Máx. 20 caracteres",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            OutlinedTextField(
                value = passwordConf,
                onValueChange = { registerViewModel.setPasswordConfirmation(it) },
                placeholder = { Text("Expl: Password123") },
                label = { Text("Confirm Password") },
                leadingIcon = { Icon(Icons.Default.LockPerson, null) },
                visualTransformation = PasswordVisualTransformation(),
                isError = showErrors && isPasswordConfError,
                modifier = Modifier.fillMaxWidth()
            )
            if (showErrors && isPasswordConfError) {
                Text(
                    "Las contraseñas no coinciden",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            CustomSpacerVertical()

            Button(
                onClick = {
                    showErrors = true
                    if (allValid){
                        onRegister()
                        registerViewModel.registerUser()
                    }
                },
                enabled = true,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ) {
                Text("Ok")
            }
        }
    }
}






