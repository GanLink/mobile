package com.ganlink.pe.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ganlink.pe.features.auth.presentation.login.DelayedView
import com.ganlink.pe.features.auth.presentation.login.Login
import com.ganlink.pe.features.auth.presentation.register.ConfirmRegister
import com.ganlink.pe.features.auth.presentation.register.Register
import com.ganlink.pe.features.bovinuesystem.presentation.BovinueForm
import com.ganlink.pe.features.farmhome.presentation.FarmAdd
import com.ganlink.pe.features.farmhome.presentation.FarmHome
import com.ganlink.pe.features.farmhome.presentation.FarmSpec

@Composable
fun AppNav(padding : PaddingValues){
    val nav = rememberNavController()

    NavHost(nav,startDestination = "delayed"){
        composable(route = "delayed") {
            DelayedView(nav)

        }
        composable(route = "login") {
            Login(
                onRegister = {
                    nav.navigate("register")
                },
                onLogin = {
                    nav.navigate("farm_home")
                }
            )
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
        composable("farm_home") {
            FarmHome(
                onFarmClick = {
                    nav.navigate("farm_spec")
                },
                onAddFarmClick = {
                    nav.navigate("farm_add")
                }
            )
        }
        composable("farm_add"){
            FarmAdd()
        }
        composable("farm_spec") {
            FarmSpec(onAddClick = {
                nav.navigate("bovinue_form")
            })
        }
        composable("bovinue_form"){
            BovinueForm()
        }
    }
}