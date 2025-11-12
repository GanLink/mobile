package com.ganlink.pe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ganlink.pe.core.navigation.AppNav
import com.ganlink.pe.core.ui.theme.GanLinkTheme
import com.ganlink.pe.features.farmmanagement.presentation.farmsettings.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GanLinkApp()
        }
    }
}

@Composable
private fun GanLinkApp() {
    val themeViewModel: ThemeViewModel = hiltViewModel()
    val darkTheme by themeViewModel.darkTheme.collectAsState(initial = false)

    GanLinkTheme(darkTheme = darkTheme) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AppNav(innerPadding)
        }
    }
}
