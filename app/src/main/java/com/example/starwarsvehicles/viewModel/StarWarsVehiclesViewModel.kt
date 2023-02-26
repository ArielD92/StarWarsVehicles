package com.example.starwarsvehicles.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsvehicles.model.Starships
import com.example.starwarsvehicles.repository.Repository
import kotlinx.coroutines.launch

class StarWarsVehiclesViewModel(
    private val repository: Repository
) : ViewModel() {
    val starships = MutableLiveData<Map<Int, String>>()
    val starshipOne = MutableLiveData<Vehicle>()
    val starshipTwo = MutableLiveData<Vehicle>()

    private val starshipNumberOne = 1
    private val starshipNumberTwo = 2
    private val defaultSpinnerValue = "Select vehicle"
    private val starshipsMap = mutableMapOf<Int, String>()

    init {
        getStarshipsNames()
    }

    fun onFirstSpinnerSelected(starshipId: Int) {
        getStarshipDetails(starshipNumber = starshipNumberOne, starshipId = starshipId)
    }

    fun onSecondSpinnerSelected(starshipId: Int) {
        getStarshipDetails(starshipNumber = starshipNumberTwo, starshipId = starshipId)
    }

    private fun getStarshipDetails(starshipNumber: Int, starshipId: Int) {
        viewModelScope.launch {
            val starshipDetailsResponse = repository.getStarshipDetails(starshipId)
            if (starshipDetailsResponse.isSuccessful) {
                starshipDetailsResponse.body()?.let {
                    val vehicle = Vehicle(
                        name = it.name,
                        model = it.model,
                        manufacturer = it.manufacturer,
                        length = it.length,
                        crew = it.crew,
                        passengers = it.passengers,
                        cargoCapacity = it.cargoCapacity,
                        hyperdriveRating = it.hyperdriveRating,
                        starshipClass = it.starshipClass
                    )
                    when (starshipNumber) {
                        starshipNumberOne -> starshipOne.postValue(vehicle)
                        starshipNumberTwo -> starshipTwo.postValue(vehicle)
                    }
                }
            }
        }
    }

    private fun getStarshipsNames() {
        var page = 0
        var hasNext: Boolean
        starshipsMap[0] = defaultSpinnerValue

        viewModelScope.launch {
            val response = repository.getStarships()
            if (response.isSuccessful) {
                response.body()?.let {
                    wrapStarshipIdAndName(it)
                    if (it.next != null) {
                        page++
                        hasNext = true
                        do {
                            val starshipsResponse = repository.getStarships(page = page)
                            val nextBody = starshipsResponse.body()
                            if (starshipsResponse.isSuccessful) {
                                nextBody?.let {
                                    if (it.next != null) {
                                        hasNext = true
                                        page++
                                    } else {
                                        hasNext = false
                                    }
                                    wrapStarshipIdAndName(it)
                                }
                            }
                        } while (hasNext)
                    }
                }
            }
            starships.postValue(starshipsMap)
        }
    }

    private fun wrapStarshipIdAndName(starships: Starships) {
        starships.results.forEach { starship ->
            starship.detailsUrl.filter { it.isDigit() }.toInt().let { starshipId ->
                starshipsMap[starshipId] = starship.name
            }
        }
    }

    class Vehicle(
        val name: String,
        val model: String,
        val manufacturer: String,
        val length: String,
        val crew: String,
        val passengers: String,
        val cargoCapacity: String,
        val hyperdriveRating: String,
        val starshipClass: String
    )
}