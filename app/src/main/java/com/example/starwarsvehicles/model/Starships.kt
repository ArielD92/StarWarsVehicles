package com.example.starwarsvehicles.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Starships(
    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val next: String?,

    @SerializedName("previous")
    val previous: String,

    @SerializedName("results")
    val results: List<Starship>,

) : Serializable