package com.example.starwarsvehicles.di

import com.example.starwarsvehicles.repository.Repository
import org.koin.dsl.module

fun repositoryModule() = module {
    single {
        Repository(get())
    }
}