package com.mobilebreakero.domain.model

import com.google.gson.annotations.SerializedName

data class RecommendedPlaces(

    @field:SerializedName("places")
    val places: List<RecommendedPlaceItem?>? = null
)

data class RecommendedPlaceItem(

    @field:SerializedName("country")
    val country: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("activity")
    val activity: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("category")
    val category: String? = null,

    var isSaved : Boolean = false,

    @field:SerializedName("bestTimeToVisit")
    val bestTimeToVisit: String? = null
)
