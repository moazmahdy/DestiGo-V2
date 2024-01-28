package com.mobilebreakero.domain.usecase

import com.mobilebreakero.domain.model.TripsItem
import com.mobilebreakero.domain.repo.RecommendedTrips
import com.mobilebreakero.domain.util.Response
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