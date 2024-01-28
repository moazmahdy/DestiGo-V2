package com.mobilebreakero.domain.usecase.firestore.trips

import com.mobilebreakero.domain.repo.TripsRepo
import javax.inject.Inject

class IsPlaceVisited @Inject constructor(
    private val repo: TripsRepo
) {
    suspend operator fun invoke(id: String, isVisited: Boolean, placeId: String) =
        repo.isPlaceVisited(isVisited, placeId, id)
}