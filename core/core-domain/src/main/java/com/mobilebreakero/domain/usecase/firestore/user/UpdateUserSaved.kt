package com.mobilebreakero.domain.usecase.firestore.user

import com.mobilebreakero.domain.model.RecommendedPlaceItem
import com.mobilebreakero.domain.model.TripsItem
import com.mobilebreakero.domain.repo.FireStoreRepository
import com.mobilebreakero.domain.repo.updateUserResponse
import javax.inject.Inject

class UpdateUserSaved @Inject constructor(
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke(
        id: String,
        savePlaces: RecommendedPlaceItem? = null,
        savedTrips: TripsItem? = null
    ): updateUserResponse = repo.updateUserSaved(id, savePlaces, savedTrips)
}
