package com.ganlink.pe.features.bovinuesystem.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BovinueForm() {
    var bovinueTypeExpanded by remember { mutableStateOf(false) }
    var selectedBovinueType by remember { mutableStateOf("Vaca") }

    var selectedMetrics by remember { mutableStateOf(listOf<String>()) }
    var metricListExpanded by remember { mutableStateOf(false) }

    val bovinueOptions = listOf("Vaca", "Toro", "Buey", "Novillo", "Ternero")
    val metricOptions = listOf(
        "Peso (kg)",
        "Edad (meses)",
        "Producción de leche (L)",
        "Salud",
        "Alimentación",
        "Tasa de preñez (%)",
        "Tasa de concepción (%)",
        "Árbol genealógico pedigree"

    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Registrar Bovino", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text(
            "Selecciona el tipo de bovino y las métricas que deseas registrar, como peso, edad o salud.",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "Tipo de Bovino",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )

        ExposedDropdownMenuBox(
            expanded = bovinueTypeExpanded,
            onExpandedChange = { bovinueTypeExpanded = !bovinueTypeExpanded }
        ) {
            TextField(
                readOnly = true,
                value = selectedBovinueType,
                onValueChange = {},
                label = { Text("Seleccionar tipo") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = bovinueTypeExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            DropdownMenu(
                expanded = bovinueTypeExpanded,
                onDismissRequest = { bovinueTypeExpanded = false }
            ) {
                bovinueOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedBovinueType = option
                            bovinueTypeExpanded = false
                        }
                    )
                }
            }
        }

        Text(
            text = "Métricas",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = if (selectedMetrics.isEmpty()) "Agregar métricas +"
                else selectedMetrics.joinToString(", "),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = if (selectedMetrics.isEmpty())
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            )

            IconButton(
                onClick = { metricListExpanded = !metricListExpanded },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar métrica",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        AnimatedVisibility(visible = metricListExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                metricOptions.forEach { metric ->
                    val isSelected = metric in selectedMetrics
                    TextButton(
                        onClick = {
                            selectedMetrics = if (isSelected) {
                                selectedMetrics - metric
                            } else {
                                selectedMetrics + metric
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val color = if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface

                        Text(
                            text = if (isSelected) "✓ $metric" else metric,
                            style = MaterialTheme.typography.bodyMedium.copy(color = color),
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        SummarySection(
            bovinueType = selectedBovinueType,
            metrics = selectedMetrics
        )
    }
}

@Composable
private fun SummarySection(bovinueType: String, metrics: List<String>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Resumen de selección:",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        Text("• Tipo de bovino: $bovinueType")
        Text("• Métricas: ${if (metrics.isEmpty()) "Ninguna seleccionada" else metrics.joinToString(", ")}")
    }
}
