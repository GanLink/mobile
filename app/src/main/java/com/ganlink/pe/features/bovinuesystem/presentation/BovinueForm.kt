package com.ganlink.pe.features.bovinuesystem.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ganlink.pe.features.bovinuesystem.presentation.models.MetricDetail
import com.ganlink.pe.features.bovinuesystem.presentation.models.bovinueMetricDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BovinueForm(
    viewModel: BovinueFormViewModel = hiltViewModel(),
    onMetricInfoClick: () -> Unit = {},
    onBovinueCreated: () -> Unit = {}
) {
    var bovinueTypeExpanded by remember { mutableStateOf(false) }
    var selectedBovinueType by remember { mutableStateOf("Vaca") }

    val submissionState by viewModel.submissionState.collectAsState()
    val metricDetails = remember { bovinueMetricDetails }
    val metricValues = remember {
        mutableStateMapOf<String, String>().apply {
            metricDetails.forEach { this[it.title] = "" }
        }
    }
    val metricErrors = remember {
        mutableStateMapOf<String, String?>().apply {
            metricDetails.forEach { this[it.title] = null }
        }
    }
    val metricSelection = remember {
        mutableStateMapOf<String, Boolean>().apply {
            metricDetails.forEach { this[it.title] = false }
        }
    }

    LaunchedEffect(submissionState.isSuccess) {
        if (submissionState.isSuccess) {
            metricValues.keys.forEach { metricValues[it] = "" }
            metricErrors.keys.forEach { metricErrors[it] = null }
            metricSelection.keys.forEach { metricSelection[it] = false }
            viewModel.clearSubmissionState()
            onBovinueCreated()
        }
    }

    val bovinueOptions = listOf("Vaca", "Toro", "Buey", "Novillo", "Ternero")
    val scrollState = rememberScrollState()

    val hasMetricInput = metricSelection.any { (title, isSelected) ->
        isSelected && metricValues[title].orEmpty().isNotBlank()
    }
    val hasErrors = metricErrors.values.any { it != null }
    val canSubmit = hasMetricInput && !hasErrors && !submissionState.isSubmitting

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Registrar Bovino", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text(
            "Selecciona el tipo de bovino y registra las métricas clave del animal para tomar mejores decisiones productivas.",
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

            androidx.compose.material3.DropdownMenu(
                expanded = bovinueTypeExpanded,
                onDismissRequest = { bovinueTypeExpanded = false }
            ) {
                bovinueOptions.forEach { option ->
                    androidx.compose.material3.DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedBovinueType = option
                            bovinueTypeExpanded = false
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Métricas productivas",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            TextButton(onClick = onMetricInfoClick) {
                Text(text = "Ver información")
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            metricDetails.forEach { detail ->
                val value = metricValues.getValue(detail.title)
                val error = metricErrors[detail.title]
                val isSelected = metricSelection[detail.title] == true

                SelectableMetricInput(
                    label = detail.title,
                    value = value,
                    isSelected = isSelected,
                    error = error,
                    onCheckedChange = { checked ->
                        metricSelection[detail.title] = checked
                        if (!checked) {
                            metricValues[detail.title] = ""
                            metricErrors[detail.title] = null
                        } else {
                            metricErrors[detail.title] = validateMetricInput(value)
                        }
                    },
                    onValueChange = { input ->
                        metricValues[detail.title] = input
                        metricErrors[detail.title] = if (metricSelection[detail.title] == true) {
                            validateMetricInput(input)
                        } else {
                            null
                        }
                    }
                )
            }
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        SummarySection(
            bovinueType = selectedBovinueType,
            metricDetails = metricDetails,
            metricValues = metricValues,
            metricSelection = metricSelection
        )

        Button(
            onClick = { viewModel.registerBovinue(metricValues.toMap()) },
            enabled = canSubmit,
            modifier = Modifier.align(Alignment.End)
        ) {
            if (submissionState.isSubmitting) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text("Registrar bovinue")
        }

        submissionState.errorMessage?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun SummarySection(
    bovinueType: String,
    metricDetails: List<MetricDetail>,
    metricValues: Map<String, String>,
    metricSelection: Map<String, Boolean>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Resumen de selección:",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        Text("• Tipo de bovino: $bovinueType")
        metricDetails.forEach { detail ->
            val value = metricValues[detail.title].orEmpty()
            val isSelected = metricSelection[detail.title] == true
            val formattedValue = if (isSelected && value.isNotBlank()) value else "-"
            Text("• ${detail.title}: $formattedValue")
        }
    }
}

private fun validateMetricInput(input: String): String? {
    if (input.isBlank()) return "Campo obligatorio"
    return if (input.toIntOrNull() == null) {
        "Ingresa un número entero"
    } else {
        null
    }
}

@Composable
private fun SelectableMetricInput(
    label: String,
    value: String,
    isSelected: Boolean,
    error: String?,
    onCheckedChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = onCheckedChange
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
        }

        if (isSelected) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text("Ingresa valor") },
                modifier = Modifier.fillMaxWidth(),
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
    }
}
