package com.ganlink.pe.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ganlink.pe.features.auth.presentation.login.DelayedView
import com.ganlink.pe.features.auth.presentation.login.Login

@Composable
fun AppNav(padding : PaddingValues){
    val nav = rememberNavController()

    NavHost(nav,startDestination = "delayed"){
        composable(route = "delayed") {
            DelayedView(nav)

        }
        composable(route = "login") {
            Login()
        }
    }
}