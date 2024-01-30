package com.mobilebreakero.auth_domain.usecase.firestore.trips

import com.mobilebreakero.auth_domain.repo.TripsRepo
import com.mobilebreakero.auth_domain.repo.getPublicTripsResponse
import javax.inject.Inject

class GetPublicTrips @Inject constructor(
    private val tripRepo: TripsRepo
) {
    suspend operator fun invoke(
        userId: String
    ): getPublicTripsResponse {
        return tripRepo.getPublicTrips(userId)
    }
}