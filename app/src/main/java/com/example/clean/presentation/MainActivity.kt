package com.example.clean.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.clean.navigation.NavHost
import com.example.clean.navigation.Screens
import com.example.clean.ui.theme.CleanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanTheme {
                Surface(color = MaterialTheme.colors.background) {
                    App()
                }
            }
        }
    }

    @Composable
    fun App(){
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screens.MainFlow.COINS.name)
    }

}