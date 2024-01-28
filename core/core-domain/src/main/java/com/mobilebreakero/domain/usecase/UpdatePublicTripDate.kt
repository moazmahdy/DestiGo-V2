package com.mobilebreakero.domain.usecase

import com.mobilebreakero.domain.repo.RecommendedTrips
import com.mobilebreakero.domain.util.Response
import javax.inject.Inject

class UpdatePublicTripDate @Inject constructor(
    private val recommendedTrips: RecommendedTrips
) {
    suspend operator fun invoke(
        tripId: String,
        startDate: String?,
        endDate: String?
    ): Response<Boolean> = recommendedTrips.updatePublicTripDate(tripId, startDate, endDate)
}