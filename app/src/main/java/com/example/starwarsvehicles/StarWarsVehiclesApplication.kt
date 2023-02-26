package com.example.starwarsvehicles

import android.app.Application
import com.example.starwarsvehicles.di.networkModule
import com.example.starwarsvehicles.di.repositoryModule
import com.example.starwarsvehicles.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StarWarsVehiclesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            androidContext(this@StarWarsVehiclesApplication)
            modules(
                listOf(
                    networkModule(),
                    viewModelModule(),
                    repositoryModule()
                )
            )
        }
    }
}