package com.example.starwarsvehicles.di

import com.example.starwarsvehicles.viewModel.StarWarsVehiclesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() = module {
    viewModel { StarWarsVehiclesViewModel(get()) }
}