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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ganlink.pe.R
import com.ganlink.pe.core.ui.components.CustomSpacerVertical

@Composable
fun Login(onRegister : () -> Unit,
          onLogin: () -> Unit){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 120.dp,bottom = 44.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .clip(shape = RoundedCornerShape(120.dp))
                    .background(brush =
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.onPrimary
                            ))),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(R.drawable.image1),
                    contentDescription = null
                )
            }
        }

        Column(modifier= Modifier.width(280.dp)) {
            OutlinedTextField(onValueChange = {},
                value = "",
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Person,
                        contentDescription = null)
                },
                placeholder = {
                    Text("Exp : Pedrito")
                },
                label = {
                    Text("Username")
                }
            )

            CustomSpacerVertical()

            OutlinedTextField(onValueChange = {},
                value = "",
                trailingIcon = {
                    Icon(imageVector = Icons.Default.RemoveRedEye,
                        contentDescription = null)
                },
                placeholder = {
                    Text("Exp : Password")
                },
                label = {
                    Text("Password")
                })

            Text(
                text = "Don't have an Account? Register",
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable {
                        onRegister()
                }
            )
        }
        CustomSpacerVertical()
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                onLogin()
            }, modifier =
                Modifier.background(color = MaterialTheme.colorScheme.background)) {
                Text("Ok")
            }
        }


    }
}