package com.mobilebreakero.domain.usecase.firestore.user

import com.mobilebreakero.domain.repo.FireStoreRepository
import javax.inject.Inject

class GetInterestedPlaces @Inject constructor(
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke(userId: String) = repo.getUserTripsBasedOnInterestedPlaces(userId)
}