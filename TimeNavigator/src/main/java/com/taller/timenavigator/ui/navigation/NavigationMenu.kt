package com.taller.timenavigator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.taller.timenavigator.ui.menu.MenuScreen
import com.taller.timenavigator.ui.screens.AddEntryScreen
import com.taller.timenavigator.ui.screens.CalendarFilterScreen
import com.taller.timenavigator.ui.screens.EditEntryScreen
import com.taller.timenavigator.ui.screens.PreviewEditEntryScreen
import com.taller.timenavigator.ui.screens.SettingsScreen
import com.taller.timenavigator.ui.viewmodels.EntryViewModel

@Composable
fun NavigationMenu(
    viewmodel: EntryViewModel
){
    val navController = rememberNavController()//Inicializamos el navController
    //Creamos el navhost para que sepa la aplicacion que pantalla mostraremos
// al principio
    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable("menu") {
            MenuScreen(navController)
        }
        composable("add") {
            AddEntryScreen(navController, viewmodel)
        }
        composable("calendar"){
            CalendarFilterScreen(navController, viewmodel)
        }
        composable("settings"){
            SettingsScreen(navController)
        }
        composable("preview/{idEntry}",
                   arguments = listOf(
                        navArgument("idEntry"){type = NavType.IntType}
                   )){
                backStackEntry ->
            val idEntry = backStackEntry.arguments?.getInt("idEntry")
            idEntry?.let {
                PreviewEditEntryScreen(navController, viewmodel, it)
            }
        }

        composable("edit/{idEntry}",
                   arguments = listOf(
                       navArgument("idEntry"){type = NavType.IntType},
                   )){
                backStackEntry ->
            val idEntry = backStackEntry.arguments?.getInt("idEntry")
            idEntry?.let {
                EditEntryScreen(navController, viewmodel, it)
            }
        }

    }
}