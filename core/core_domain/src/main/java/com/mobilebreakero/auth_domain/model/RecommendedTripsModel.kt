package com.mobilebreakero.auth_domain.model

import com.google.gson.annotations.SerializedName

data class RecommendedTripsModel(

    @field:SerializedName("trips")
    val trips: List<TripsItem?>? = null
)

data class TripsItem(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("places_to_visit")
    val placesToVisit: List<PlacesToVisitItem?>? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    val userId: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    val tripType: String = "publicTrip",

    val tripId: String,

    val isSaved: Boolean? = false,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("full_journey")
    val fullJourney: FullJourney? = null,

    @field:SerializedName("reason_for_travel")
    val reasonForTravel: String? = null
) {
    companion object {
        const val COLLECTION_NAME = "publicTrips"
    }

    constructor() : this(
        image = "",
        placesToVisit = emptyList(),
        description = "",
        id = 0,
        userId = "",
        title = "",
        tripId = "",
        category = "",
        fullJourney = FullJourney(),
        reasonForTravel = ""
    )
}


data class FullJourney(

    @field:SerializedName("end_date")
    val endDate: String? = null,

    @field:SerializedName("sleep")
    val sleep: String? = null,

    @field:SerializedName("number_of_days")
    val numberOfDays: String? = null,

    @field:SerializedName("activities")
    val activities: List<String?>? = null,

    @field:SerializedName("eat")
    val eat: String? = null,

    @field:SerializedName("start_date")
    var startDate: String? = null
)

data class PlacesToVisitItem(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("name")
    val name: String? = null
)
