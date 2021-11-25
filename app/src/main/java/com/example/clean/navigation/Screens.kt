package com.example.clean.navigation

object Screens {
    enum class MainFlow(var args: String = "") {
        COINS,
        COIN_DETAIL("/{coinId}")
    }
}