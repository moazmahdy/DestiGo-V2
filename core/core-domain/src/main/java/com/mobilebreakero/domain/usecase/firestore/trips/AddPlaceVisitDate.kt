package com.mobilebreakero.domain.usecase.firestore.trips

import com.mobilebreakero.domain.repo.TripsRepo
import javax.inject.Inject

class AddPlaceVisitDate @Inject constructor(
    private val repo: TripsRepo
) {
    suspend operator fun invoke(date: String, placeId: String, id: String) =
        repo.addPlaceVisitDate(
            date = date,
            placeId = placeId,
            tripId = id
        )

}
