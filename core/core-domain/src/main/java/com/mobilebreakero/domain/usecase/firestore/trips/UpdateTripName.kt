package com.mobilebreakero.domain.usecase.firestore.trips

import com.mobilebreakero.domain.repo.TripsRepo
import com.mobilebreakero.domain.repo.updateTripResponse
import javax.inject.Inject

class UpdateTripName @Inject constructor(
    private val repo: TripsRepo
) {
    suspend operator fun invoke(
        tripName: String,
        tripId: String
    ) : updateTripResponse= repo.updateTripName(tripName, tripId)
}