package com.mobilebreakero.auth_domain.usecase.firestore.trips

import com.mobilebreakero.auth_domain.repo.TripsRepo
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