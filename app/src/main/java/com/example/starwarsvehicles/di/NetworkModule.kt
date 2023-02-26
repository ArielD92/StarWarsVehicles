package com.example.starwarsvehicles.di

import com.example.starwarsvehicles.api.StarWarsApiService
import com.example.starwarsvehicles.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun networkModule() = module {
    single {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single {
        OkHttpClient.Builder()
            .cache(null)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            })
            .retryOnConnectionFailure(true)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(StarWarsApiService::class.java)
    }
}