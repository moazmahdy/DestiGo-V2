package com.mobilebreakero.auth_domain.usecase

import com.mobilebreakero.auth_domain.repo.RecommendedTrips
import javax.inject.Inject

class RecommendedUseCase @Inject constructor(
    private val recommendedTrips: RecommendedTrips
) {
    suspend fun getRecommendation(userInterests: List<String>) = recommendedTrips.getRecommendation(userInterests)
}