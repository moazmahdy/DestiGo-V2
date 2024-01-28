package com.mobilebreakero.domain.usecase.firestore.trips

import com.mobilebreakero.domain.repo.TripsRepo
import com.mobilebreakero.domain.repo.updateTripResponse
import javax.inject.Inject

class UpdateTripDays @Inject constructor(
private val repo: TripsRepo
) {
    suspend operator fun invoke(
        tripDays: String,
        tripId: String
    ) : updateTripResponse= repo.updateTripDays(tripDays, tripId)
}