package com.example.clean.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.clean.navigation.Screens
import com.example.clean.presentation.coins.composes.CoinsScreen


val mainGraph: NavGraphBuilder.(navController: NavController) -> Unit = { navController ->

    composable(
        route = Screens.MainFlow.COINS.name
    ) {
        CoinsScreen(navController)
    }

    composable(
        route = Screens.MainFlow.COIN_DETAIL.name.plus(Screens.MainFlow.COIN_DETAIL.args)
    ) {
        CoinsScreen(navController)
    }

}