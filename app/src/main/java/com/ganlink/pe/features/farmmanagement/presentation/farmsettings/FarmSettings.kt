package com.ganlink.pe.features.farmmanagement.presentation.farmsettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmSettings(
    onBack: (() -> Unit)? = null,
    viewModel: FarmSettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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

            SettingSwitchRow(
                title = "Notificaciones",
                description = "Recibir alertas sobre eventos o tareas pendientes.",
                icon = Icons.Default.Notifications,
                checked = state.notificationsEnabled,
                onCheckedChange = { viewModel.setNotifications(it) }
            )

            SettingSwitchRow(
                title = "Modo oscuro",
                description = "Cambia el tema de la aplicación.",
                icon = Icons.Default.Palette,
                checked = state.darkModeEnabled,
                onCheckedChange = { viewModel.setDarkMode(it) }
            )

            SettingSwitchRow(
                title = "Sincronización automática",
                description = "Sincroniza los datos con el servidor al iniciar la app.",
                icon = Icons.Default.Wifi,
                checked = state.autoSyncEnabled,
                onCheckedChange = { viewModel.setAutoSync(it) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        viewModel.applyCurrentSettings()
                        scope.launch {
                            snackbarHostState.showSnackbar("Cambios guardados")
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Guardar cambios")
                }

                OutlinedButton(
                    onClick = { viewModel.resetDefaults() },
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
