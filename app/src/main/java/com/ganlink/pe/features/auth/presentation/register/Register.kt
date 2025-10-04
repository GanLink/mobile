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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganlink.pe.R
import com.ganlink.pe.core.ui.components.CustomSpacer
import com.ganlink.pe.core.ui.components.CustomSpacerVertical

@Composable
fun Register(){
    Column(modifier = Modifier.fillMaxSize())
    {
        Spacer(modifier = Modifier.padding(top = 80.dp))
        Row(modifier = Modifier.fillMaxWidth()
            .height(200.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Box(modifier = Modifier.background(
                brush = Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.onPrimary
                    )
                )
            )){
                Text("Register",
                    style = MaterialTheme.typography.headlineLarge)
            }

            CustomSpacer()
            Image(
                painter = painterResource(R.drawable.image1),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
fun dafas(){
    Register()
}