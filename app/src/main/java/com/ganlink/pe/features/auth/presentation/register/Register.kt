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
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockPerson
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganlink.pe.R
import com.ganlink.pe.core.ui.components.CustomSpacer
import com.ganlink.pe.core.ui.components.CustomSpacerVertical

@Composable
fun Register(onRegister : () -> Unit){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(modifier = Modifier.padding(top = 80.dp))
        Row(modifier = Modifier.fillMaxWidth()
            .height(200.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Box(modifier = Modifier.clip(shape = RoundedCornerShape(200.dp))
                .background(color = MaterialTheme.colorScheme.secondaryContainer).size(width = 150.dp,80.dp),
                contentAlignment = Alignment.Center
            ){
                Text("Register",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.secondary)
            }

            CustomSpacer()
            Image(
                painter = painterResource(R.drawable.image1),
                contentDescription = null
            )
        }
        CustomSpacerVertical()
        Column(modifier = Modifier.width(240.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            OutlinedTextField(onValueChange = {},
                value = "",
                placeholder = {
                    Text("Expl: Pepe")
                },
                label = {
                    Text("First Name")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.DriveFileRenameOutline,
                        contentDescription = null)
                }
            )
            OutlinedTextField(onValueChange = {},
                value = "",
                placeholder = {
                    Text("Expl: McCurntey")
                },
                label = {
                    Text("Last Name")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.PersonAdd,
                        contentDescription = null)
                }
            )
            OutlinedTextField(onValueChange = {},
                value = "",
                placeholder = {
                    Text("Expl: 10123456789")
                },
                label = {
                    Text("RUC")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.FileCopy,
                        contentDescription = null)
                }
            )
            OutlinedTextField(onValueChange = {},
                value = "",
                placeholder = {
                    Text("Expl: Password123")
                },
                label = {
                    Text("Password")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock,
                        contentDescription = null)
                }
            )
            OutlinedTextField(onValueChange = {},
                value = "",
                placeholder = {
                    Text("Expl: Password123")
                },
                label = {
                    Text("Confirm Password")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.LockPerson,
                        contentDescription = null)
                }
            )
            CustomSpacerVertical()
            Button(onClick = {
                onRegister()
            }, modifier =
                Modifier.background(color = MaterialTheme.colorScheme.background)) {
                Text("Ok")
            }
        }
    }
}

@Composable
@Preview
fun dafas(){
    Register(){}
}