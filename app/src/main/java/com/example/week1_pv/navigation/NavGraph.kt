package com.example.week1_pv

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.week1_pv.navigation.Screen
import com.example.week1_pv.view.AnimatedSplashScreen
import com.example.week1_pv.view.HomeScreen
import com.example.week1_pv.view.FormScreen


@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(
            route = Screen.SplashScreen.route
        ) {
            AnimatedSplashScreen(navController = navController)
        }
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.FormScreen.route,
            arguments = listOf(
                navArgument("title") {
                    type = NavType.StringType
                    defaultValue = "Tambah"
            },
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            var title = it.arguments?.getString("title")!!
            var id = it.arguments?.getString("id")!!
            FormScreen(navController = navController, title = title, id = id)
        }
    }
}