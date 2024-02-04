package com.mobilebreakero.auth_domain.usecase

import com.mobilebreakero.auth_domain.repo.RecommendedTrips
import com.mobilebreakero.auth_domain.util.Response
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