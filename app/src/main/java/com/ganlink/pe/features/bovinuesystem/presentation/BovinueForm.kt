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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BovinueForm() {
    var bovinueType by remember { mutableStateOf("Vaca") }
    var metricExpanded by remember { mutableStateOf(false) }
    var selectedMetric by remember { mutableStateOf<String?>(null) }
    var metricListExpanded by remember { mutableStateOf(false) }

    val bovinueOptions = listOf("Vaca", "Toro", "Buey", "Novillo", "Ternero")
    val metricOptions = listOf("Peso (kg)", "Edad (meses)", "Producción Leche (L)", "Salud", "Alimentación")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Tipo de Bovinue",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )

        ExposedDropdownMenuBox(
            expanded = metricExpanded,
            onExpandedChange = { metricExpanded = !metricExpanded }
        ) {
            TextField(
                readOnly = true,
                value = bovinueType,
                onValueChange = {},
                label = { Text("Seleccionar tipo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = metricExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor()
            )

            DropdownMenu(
                expanded = metricExpanded,
                onDismissRequest = { metricExpanded = false }
            ) {
                bovinueOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            bovinueType = option
                            metricExpanded = false
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
                text = selectedMetric ?: "Metric +",
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = if (selectedMetric == null)
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
                    contentDescription = "Add metric",
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
                    TextButton(
                        onClick = {
                            selectedMetric = metric
                            metricListExpanded = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = metric,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }
    }
}
