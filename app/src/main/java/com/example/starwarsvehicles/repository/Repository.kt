package com.example.starwarsvehicles.repository

import com.example.starwarsvehicles.api.StarWarsApiService

class Repository(private val starWarsApiService: StarWarsApiService) {
    suspend fun getStarships(page: Int? = null) = starWarsApiService.getStarships(page = page)

    suspend fun getStarshipDetails(starshipId: Int) =
        starWarsApiService.getStarshipDetails(starshipId = starshipId)
}