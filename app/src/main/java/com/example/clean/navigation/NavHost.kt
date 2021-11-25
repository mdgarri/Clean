package com.example.clean.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.clean.navigation.graphs.mainGraph

@Composable
fun NavHost(navController: NavHostController, startDestination: String) {
    androidx.navigation.compose.NavHost(navController = navController, startDestination = startDestination) {

        mainGraph(navController)

    }
}