package com.ganlink.pe.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ganlink.pe.features.auth.presentation.login.DelayedView
import com.ganlink.pe.features.auth.presentation.login.Login
import com.ganlink.pe.features.auth.presentation.register.ConfirmRegister
import com.ganlink.pe.features.auth.presentation.register.Register

@Composable
fun AppNav(padding : PaddingValues){
    val nav = rememberNavController()

    NavHost(nav,startDestination = "delayed"){
        composable(route = "delayed") {
            DelayedView(nav)

        }
        composable(route = "login") {
            Login {
                nav.navigate("register")
            }
        }
        composable(route = "register") {
            Register{
                nav.navigate("confirm_register"){
                    popUpTo("register") { inclusive = true }
                }

            }
        }
        composable(route = "confirm_register") {
            ConfirmRegister{
                nav.navigate("login"){
                    popUpTo("confirm_register") { inclusive = true }
                }
            }
        }
    }
}