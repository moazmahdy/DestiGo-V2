package com.mobilebreakero.domain.usecase.firestore.trips

import com.mobilebreakero.domain.repo.TripsRepo
import javax.inject.Inject

class GetTripsByCategories @Inject constructor(
    private val tripsRepo: TripsRepo
) {
    suspend operator fun invoke(categories: String) = tripsRepo.getTripsByCategory(categories)
}