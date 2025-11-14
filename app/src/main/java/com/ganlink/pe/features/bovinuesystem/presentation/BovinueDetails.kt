package com.ganlink.pe.features.bovinuesystem.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BovinueDetails(
    viewModel: BovinueDetailsViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val uiState by viewModel.state.collectAsState()
    var metricToEdit by remember { mutableStateOf<BovinueMetricUi?>(null) }
    var editValue by remember { mutableStateOf("") }
    var editError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Bovinue #${viewModel.bovinueId}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.refresh() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Actualizar"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.errorMessage != null -> {
                    ErrorState(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        message = uiState.errorMessage!!,
                        onRetry = { viewModel.refresh() }
                    )
                }

                else -> {
                    MetricsContent(
                        padding = padding,
                        metrics = uiState.metrics,
                        onEdit = { metric ->
                            metricToEdit = metric
                            editValue = metric.quantity.toString()
                            editError = null
                        }
                    )
                }
            }

            if (uiState.isUpdating) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                )
            }
        }
    }

    metricToEdit?.let { target ->
        EditMetricDialog(
            metric = target,
            value = editValue,
            error = editError,
            onValueChange = {
                editValue = it
                editError = null
            },
            onDismiss = {
                metricToEdit = null
                editError = null
            },
            onConfirm = {
                val newQuantity = editValue.toIntOrNull()
                if (newQuantity == null) {
                    editError = "Ingresa un número válido"
                } else {
                    viewModel.updateMetric(target, newQuantity)
                    metricToEdit = null
                }
            }
        )
    }
}

@Composable
private fun MetricsContent(
    padding: PaddingValues,
    metrics: List<BovinueMetricUi>,
    onEdit: (BovinueMetricUi) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            OverviewCard(metricsCount = metrics.size)
        }

        if (metrics.isEmpty()) {
            item {
                EmptyMetrics()
            }
        } else {
            items(metrics, key = { it.id }) { metric ->
                MetricCard(
                    metric = metric,
                    onEdit = onEdit
                )
            }
        }
    }
}

@Composable
private fun OverviewCard(metricsCount: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Resumen general",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Total de métricas registradas: $metricsCount",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun MetricCard(
    metric: BovinueMetricUi,
    onEdit: (BovinueMetricUi) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = metric.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = metric.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Valor registrado: ${metric.quantity}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Fecha: ${metric.dateLabel}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { onEdit(metric) }) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Editar")
                }
            }
        }
    }
}

@Composable
private fun EmptyMetrics() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Sin métricas registradas",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Cuando registres métricas desde el formulario del bovinue, aparecerán aquí para su seguimiento.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ErrorState(
    modifier: Modifier = Modifier,
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ocurrió un problema",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Reintentar")
        }
    }
}

@Composable
private fun EditMetricDialog(
    metric: BovinueMetricUi,
    value: String,
    error: String?,
    onValueChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Editar ${metric.title}") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Valor actual: ${metric.quantity}",
                    style = MaterialTheme.typography.bodyMedium
                )
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    label = { Text("Nuevo valor") },
                    singleLine = true,
                    isError = error != null
                )
                if (error != null) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Guardar cambios")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
