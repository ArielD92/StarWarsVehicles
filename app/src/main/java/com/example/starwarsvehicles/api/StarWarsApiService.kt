package com.example.starwarsvehicles.api

import com.example.starwarsvehicles.model.Starship
import com.example.starwarsvehicles.model.Starships
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsApiService {

    @GET("starships/")
    suspend fun getStarships(
        @Query("page") page: Int? = null
    ): Response<Starships>

    @GET("starships/{id}")
    suspend fun getStarshipDetails(
        @Path("id") starshipId: Int)
    : Response<Starship>
}