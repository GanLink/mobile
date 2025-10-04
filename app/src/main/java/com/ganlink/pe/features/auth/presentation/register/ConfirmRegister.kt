package com.ganlink.pe.features.auth.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ganlink.pe.core.ui.components.CustomSpacerVertical

@Composable
fun ConfirmRegister(onReturn : () -> Unit){
    Column(modifier = Modifier.fillMaxSize().
    background(color = MaterialTheme.colorScheme.secondaryContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.clip(RoundedCornerShape(200.dp))
            .background(color = Color.White)
            .size(140.dp),
            contentAlignment = Alignment.Center){

                Icon(imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(130.dp))

        }
        CustomSpacerVertical()
        Row(horizontalArrangement = Arrangement.Center) {
            Text("Congratulations! You are account will be activate soon . . .",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.width(280.dp))
        }

        CustomSpacerVertical()
        Button(onClick = {
            onReturn()
        }) {
            Text("Return Home")
        }
    }
}