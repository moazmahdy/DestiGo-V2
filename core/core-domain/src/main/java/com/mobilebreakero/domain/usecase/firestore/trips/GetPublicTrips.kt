package com.mobilebreakero.domain.usecase.firestore.trips

import com.mobilebreakero.domain.repo.TripsRepo
import com.mobilebreakero.domain.repo.getPublicTripsResponse
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