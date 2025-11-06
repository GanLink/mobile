package com.ganlink.pe.features.farmmanagement.presentation.farmspec

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ganlink.pe.features.farmmanagement.presentation.models.MainActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmAdd(
    vm: FarmAddViewModel = hiltViewModel(),
    //onSubmit: (alias: String, mainActivityCode: Int, ownerDni: String) -> Unit
) {
    val alias by vm.alias.collectAsState()
    val ownerDni by vm.ownerDni.collectAsState()
    val mainActivity by vm.mainActivity.collectAsState()

    val aliasError by vm.aliasError.collectAsState()
    val dniError by vm.dniError.collectAsState()
    val activityError by vm.activityError.collectAsState()

    var activityMenuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 140.dp , bottom = 20.dp,
                start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Registrar Granja", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text(
            "Actividad principal: Carne (1: engorde/ceba), Leche (2: ordeño/derivados), Genérica (3: mixta).",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = alias,
            onValueChange = vm::setAlias,
            label = { Text("Alias de la granja") },
            placeholder = { Text("Ej: Fundo Santa Rosa") },
            isError = aliasError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (aliasError != null) {
            Text(aliasError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = activityMenuExpanded,
            onExpandedChange = { activityMenuExpanded = !activityMenuExpanded }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = mainActivity?.let { "${it.name.lowercase().replaceFirstChar { c -> c.titlecase() }} (${it.code})" } ?: "",
                onValueChange = {},
                label = { Text("Actividad principal") },
                placeholder = { Text("Selecciona una actividad") },
                isError = activityError != null,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = activityMenuExpanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = activityMenuExpanded,
                onDismissRequest = { activityMenuExpanded = false }
            ) {
                MainActivity.values().forEach { option ->
                    DropdownMenuItem(
                        text = { Text("${option.name.lowercase().replaceFirstChar { it.titlecase() }} (${option.code})") },
                        onClick = {
                            vm.setMainActivity(option)
                            activityMenuExpanded = false
                        }
                    )
                }
            }
        }
        if (activityError != null) {
            Text(activityError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = ownerDni,
            onValueChange = vm::setOwnerDni,
            label = { Text("DNI del propietario") },
            placeholder = { Text("Ej: 12345678") },
            isError = dniError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        if (dniError != null) {
            Text(dniError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = {
                if (vm.validate()) {
                    val code = vm.mainActivity.value!!.code
                    //onSubmit(alias.trim(), code, ownerDni.trim())
                }
            }) {
                Text("Guardar")
            }
            OutlinedButton(onClick = { vm.clear() }) {
                Text("Limpiar")
            }
        }
    }
}
