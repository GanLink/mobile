package com.ganlink.pe.features.bovinuesystem.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ganlink.pe.features.bovinuesystem.presentation.models.MetricField

@Composable
fun MetricInputCard(
    metric: MetricField,
    onUpdate: (MetricField) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Checkbox(
                    checked = metric.checked,
                    onCheckedChange = {
                        onUpdate(metric.copy(checked = it, value = if (!it) "" else metric.value))
                    }
                )
                Text(
                    text = metric.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                )
            }

            if (metric.checked) {
                OutlinedTextField(
                    value = metric.value,
                    onValueChange = { newValue ->
                        if (metric.keyboardType == KeyboardType.Number) {
                            if (newValue.isNumeric()) onUpdate(metric.copy(value = newValue))
                        } else {
                            onUpdate(metric.copy(value = newValue))
                        }
                    },
                    placeholder = { Text(metric.placeholder) },
                    keyboardOptions = KeyboardOptions(keyboardType = metric.keyboardType),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
                    singleLine = true
                )
            }
        }
    }
}

private fun String.isNumeric(): Boolean = this.matches(Regex("^\\d*\\.?\\d*$"))