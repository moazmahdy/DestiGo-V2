package com.mobilebreakero.domain.usecase.firestore

import com.mobilebreakero.domain.repo.TripsRepo
import com.mobilebreakero.domain.repo.updateTripResponse
import javax.inject.Inject

class IsTripFinished @Inject constructor(
    private val tripRepo: TripsRepo
) {
    suspend operator fun invoke(tripId: String, finished: Boolean): updateTripResponse {
        return tripRepo.isTripFinished(tripId, finished = finished)
    }
}
