package com.ganlink.pe.features.bovinuesystem.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ganlink.pe.R
import com.ganlink.pe.features.bovinuesystem.presentation.components.MetricInputCard
import com.ganlink.pe.features.bovinuesystem.presentation.models.MetricField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BovinueDetails() {
    val cowName = "Vaca 001 - Santa Rosa"
    val breed = "Holstein"
    val age = "4 años"
    val weight = "580 kg"
    val health = "Buena"
    val location = "Corral 3"

    var metrics by remember {
        mutableStateOf(
            listOf(
                MetricField(
                    title = "Producción de leche por vaca/día (L)",
                    placeholder = "Ej: 25",
                    keyboardType = KeyboardType.Number
                ),
                MetricField(
                    title = "Contenido de grasa y proteína (%)",
                    placeholder = "Ej: 3.8",
                    keyboardType = KeyboardType.Number
                ),
                MetricField(
                    title = "Ganancia de peso diaria (GMD) (kg/día)",
                    placeholder = "Ej: 1.2",
                    keyboardType = KeyboardType.Number
                ),
                MetricField(
                    title = "Índice de conversión alimenticia (kg alimento/kg ganado)",
                    placeholder = "Ej: 6.5",
                    keyboardType = KeyboardType.Number
                ),
                MetricField(
                    title = "Tasa de preñez (%)",
                    placeholder = "Ej: 85",
                    keyboardType = KeyboardType.Number
                ),
                MetricField(
                    title = "Tasa de concepción (%)",
                    placeholder = "Ej: 65",
                    keyboardType = KeyboardType.Number
                )
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "Detalles del Bovino",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image1),
                    contentDescription = "Foto de vaca",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(cowName, style = MaterialTheme.typography.titleMedium)
                    Text("Raza: $breed", style = MaterialTheme.typography.bodyMedium)
                    Text("Edad: $age", style = MaterialTheme.typography.bodyMedium)
                    Text("Peso: $weight", style = MaterialTheme.typography.bodyMedium)
                    Text("Salud: $health", style = MaterialTheme.typography.bodyMedium)
                    Text("Ubicación: $location", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Text(
            text = "Métricas productivas",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f, fill = true)
        ) {
            items(metrics, key = { it.title }) { metric ->
                MetricInputCard(
                    metric = metric,
                    onUpdate = { updated ->
                        metrics = metrics.map {
                            if (it.title == updated.title) updated else it
                        }
                    }
                )
            }

            item {
                Spacer(Modifier.height(100.dp))
            }
        }

        Button(
            onClick = {
                val filled = metrics.filter { it.checked && it.value.isNotBlank() }
                println("Guardando métricas: $filled")
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Default.Check, contentDescription = null)
            Spacer(Modifier.width(6.dp))
            Text("Guardar métricas")
        }
    }
}
