package com.example.clean.navigation

object Screens {

    interface Screen {
        fun getRoute(): String
    }

    enum class MainFlow(private val args: Array<String> = emptyArray()): Screen {
        COINS,
        COIN_DETAIL( arrayOf("coinId") );

        override fun getRoute(): String {
            return if (args.isEmpty()) name
            else  name + args.joinToString(separator = "", prefix = "/{", postfix = "}")
        }
    }


}