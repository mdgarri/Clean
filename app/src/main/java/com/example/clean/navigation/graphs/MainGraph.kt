package com.example.clean.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.clean.navigation.Screens
import com.example.clean.presentation.coin_details.composes.CoinDetailScreen
import com.example.clean.presentation.coins.composes.CoinsScreen


val mainGraph: NavGraphBuilder.(navController: NavController) -> Unit = { navController ->

    composable(
        route = Screens.MainFlow.COINS.getRoute()
    ) {
        CoinsScreen(navController)
    }

    composable(
        route = Screens.MainFlow.COIN_DETAIL.getRoute(),
        arguments = listOf(
            navArgument("coinId") {
                type = NavType.StringType
            }
        )
    ) { navBackStackEntry ->
        CoinDetailScreen(navController, navBackStackEntry.arguments?.getString("coinId") ?: "")
    }

}