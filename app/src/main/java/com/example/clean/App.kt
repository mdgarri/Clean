package com.example.clean

import android.app.Application
import com.example.clean.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin(){
        startKoin {
            androidContext(this@App)
            modules(
                apiServicesKoinModule,
                commonKoinModule,
                domainKoinModule,
                repositoriesKoinModule,
                viewModelsKoinModule
            )
        }
    }

}