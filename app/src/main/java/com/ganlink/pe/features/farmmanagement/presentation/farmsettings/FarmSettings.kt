package com.ganlink.pe.features.farmmanagement.presentation.farmsettings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmSettings(
    onBack: (() -> Unit)? = null
) {
    var farmName by remember { mutableStateOf("Mi granja principal") }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var autoSyncEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración de Granja") },
                navigationIcon = {
                    onBack?.let {
                        IconButton(onClick = it) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                "Ajustes generales de la granja",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium)
            )

            OutlinedTextField(
                value = farmName,
                onValueChange = { farmName = it },
                label = { Text("Nombre de la granja") },
                placeholder = { Text("Ej: Fundo Santa Rosa") },
                modifier = Modifier.fillMaxWidth()
            )

            SettingSwitchRow(
                title = "Notificaciones",
                description = "Recibir alertas sobre eventos o tareas pendientes.",
                icon = Icons.Default.Notifications,
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )

            SettingSwitchRow(
                title = "Modo oscuro",
                description = "Cambia el tema de la aplicación.",
                icon = Icons.Default.Palette,
                checked = darkModeEnabled,
                onCheckedChange = { darkModeEnabled = it }
            )

            SettingSwitchRow(
                title = "Sincronización automática",
                description = "Sincroniza los datos con el servidor al iniciar la app.",
                icon = Icons.Default.Wifi,
                checked = autoSyncEnabled,
                onCheckedChange = { autoSyncEnabled = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Guardar cambios")
                }

                OutlinedButton(
                    onClick = {
                        farmName = "Mi granja principal"
                        notificationsEnabled = true
                        darkModeEnabled = false
                        autoSyncEnabled = true
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Restablecer")
                }
            }
        }
    }
}

@Composable
private fun SettingSwitchRow(
    title: String,
    description: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
