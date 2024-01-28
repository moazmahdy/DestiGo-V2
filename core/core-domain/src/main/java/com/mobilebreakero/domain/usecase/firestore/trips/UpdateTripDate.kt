package com.mobilebreakero.domain.usecase.firestore.trips

import com.mobilebreakero.domain.repo.TripsRepo
import com.mobilebreakero.domain.repo.updateTripResponse
import javax.inject.Inject

class UpdateTripDate @Inject constructor(
    private val repo: TripsRepo
) {
    suspend operator fun invoke(
        tripDate: String,
        tripId: String
    ) : updateTripResponse= repo.updateTripDate(tripDate, tripId)
}