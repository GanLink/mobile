package com.ganlink.pe.features.auth.presentation.login

import com.ganlink.pe.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganlink.pe.core.ui.components.CustomSpacer

@Composable
fun DelayedView(){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(id = R.drawable.image1),
            contentDescription = null
        )
        Spacer(
            modifier = Modifier.padding(top = 16.dp)
        )
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Loading"
            )
            CustomSpacer()
            CircularProgressIndicator(
                color = Color.Blue,
                strokeWidth = 2.dp,
                modifier = Modifier.size(40.dp)
            )

        }


    }
}

@Composable
@Preview
fun preview(){
    DelayedView()
}