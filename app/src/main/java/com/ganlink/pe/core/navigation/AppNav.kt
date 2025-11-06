package com.ganlink.pe.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ganlink.pe.features.auth.presentation.login.DelayedView
import com.ganlink.pe.features.auth.presentation.login.Login
import com.ganlink.pe.features.auth.presentation.register.ConfirmRegister
import com.ganlink.pe.features.auth.presentation.register.Register
import com.ganlink.pe.features.bovinuesystem.presentation.BovinueDetails
import com.ganlink.pe.features.bovinuesystem.presentation.BovinueForm
import com.ganlink.pe.features.farmmanagement.presentation.farmspec.FarmAdd
import com.ganlink.pe.features.farmmanagement.presentation.farmhome.FarmHome
import com.ganlink.pe.features.farmmanagement.presentation.farmhome.FarmHomeViewModel
import com.ganlink.pe.features.farmmanagement.presentation.farmsettings.FarmSettings
import com.ganlink.pe.features.farmmanagement.presentation.farmsettings.FarmSpec

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
                onLogin = { id, username ->
                    nav.navigate("farm_home/$id/$username")
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
        composable("farm_home/{id}/{username}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                },
                navArgument("username"){
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            backStackEntry.arguments?.let { arguments ->
                val id = arguments.getInt("id")
                val username = arguments.getString("username")
                val farmHomeViewModel : FarmHomeViewModel = hiltViewModel()
                farmHomeViewModel.getFarmsById(id)
                FarmHome(
                    viewModel= farmHomeViewModel,
                    username = username!!,
                    onFarmClick = {
                        nav.navigate("farm_spec")
                    },
                    onAddFarmClick = {
                        nav.navigate("farm_add")
                    }
                )
            }
        }
        composable("farm_add"){
            FarmAdd()
        }
        composable("farm_spec") {
            FarmSpec(
                onAddClick = {
                nav.navigate("bovinue_form")
            },
                onSettingsClick = {
                    nav.navigate("farm_settings")
                },
                onCardClick = {
                    nav.navigate("bovinue_details")
                })
        }
        composable("farm_settings"){
            FarmSettings()
        }
        composable("bovinue_form"){
            BovinueForm()
        }
        composable("bovinue_details") {
            BovinueDetails()
        }
    }
}