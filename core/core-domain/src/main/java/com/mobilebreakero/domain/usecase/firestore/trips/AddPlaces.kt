package com.mobilebreakero.domain.usecase.firestore.trips

import com.mobilebreakero.domain.model.TripPlace
import com.mobilebreakero.domain.repo.TripsRepo
import javax.inject.Inject

class AddPlaces @Inject constructor(
    private val repo: TripsRepo
) {
    suspend operator fun invoke(
        placeName: String, placeId: String, id: String, placeTripId: String
    ) =
        repo.addPlaces(
            placeName = placeName,
            placeId = placeId,
            id = id,
            placeTripId = placeTripId
        )

}