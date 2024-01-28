package com.mobilebreakero.domain.usecase

import com.mobilebreakero.domain.repo.RecommendedTrips
import javax.inject.Inject

class UpdatePublicTripDays @Inject constructor(
    private val recommendedTrips: RecommendedTrips
) {
    suspend fun updatePublicTripDate(
        tripId: String, days: String
    ) = recommendedTrips.updatePublicTripDays(tripId, days)
}