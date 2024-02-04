package com.mobilebreakero.auth_domain.usecase.firestore.trips

import com.mobilebreakero.auth_domain.repo.TripsRepo
import com.mobilebreakero.auth_domain.repo.updateTripResponse
import javax.inject.Inject

class UpdateTripDays @Inject constructor(
private val repo: TripsRepo
) {
    suspend operator fun invoke(
        tripDays: String,
        tripId: String
    ) : updateTripResponse= repo.updateTripDays(tripDays, tripId)
}