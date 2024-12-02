package com.taller.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.taller.myapplication.ui.menu.MenuScreen
import com.taller.myapplication.ui.screens.AddEntryScreen
import com.taller.myapplication.ui.screens.EditEntryScreen
import com.taller.myapplication.ui.screens.ListScreen
import com.taller.myapplication.ui.screens.SettingsScreen
import com.taller.myapplication.ui.viewmodels.EntryViewModel

@Composable
fun NavigationMenu(
    viewmodel: EntryViewModel
){
    var navController = rememberNavController()//Inicializamos el navController
    //Creamos el navhost para que sepa la aplicacion que pantalla mostraremos
// al principio
    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable("menu") {
            MenuScreen(navController)
        }
        composable("list"){
            //MenuScreen(navController, viewmodel)
            ListScreen(navController, viewmodel)
        }
        composable("add") {
            AddEntryScreen(navController, viewmodel)
        }
        composable("settings"){
            SettingsScreen(navController)
        }
        composable("edit/{idEntry}/{mood}/{score}/{memory}/{day}/{month}/{year}",
                   arguments = listOf(
                       navArgument("idEntry"){type = NavType.StringType},
                       navArgument("mood"){type = NavType.StringType},
                       navArgument("score"){type = NavType.StringType},
                       navArgument("memory"){type = NavType.StringType},
                       navArgument("day"){type = NavType.StringType},
                       navArgument("month"){type = NavType.StringType},
                       navArgument("year"){type = NavType.StringType},
                   )){
            EditEntryScreen(
                navController,
                viewmodel,
                it.arguments?.getString("idEntry")!!,
                it.arguments?.getString("mood")!!,
                it.arguments?.getString("score")!!,
                it.arguments?.getString("memory")!!,
                it.arguments?.getString("day")!!,
                it.arguments?.getString("month")!!,
                it.arguments?.getString("year")!!,
            )
        }
    }
}