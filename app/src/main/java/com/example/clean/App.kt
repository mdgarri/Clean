package com.example.clean

import android.app.Application
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin(){
        startKoin {

        }
    }

}