package com.mobilebreakero.auth_domain.usecase

import com.mobilebreakero.auth_domain.model.TripsItem
import com.mobilebreakero.auth_domain.repo.RecommendedTrips
import com.mobilebreakero.auth_domain.util.Response
import javax.inject.Inject

class RecommendedPlaceUseCase @Inject constructor(
    private val repo: RecommendedTrips
) {
    suspend fun getRecommendationPlaces(userInterests: List<String>) =
        repo.getRecommendationPlaces(userInterests)
}

class GetPublicTripsUseCase @Inject constructor(
    private val repo: RecommendedTrips
) {
    suspend operator fun invoke(userId: String): Response<TripsItem?> =
        repo.getPublicTrips(userId)
}