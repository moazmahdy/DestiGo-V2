package com.mobilebreakero.auth_domain.usecase.firestore.user

import com.mobilebreakero.auth_domain.repo.FireStoreRepository
import javax.inject.Inject

class GetInterestedPlaces @Inject constructor(
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke(userId: String) = repo.getUserTripsBasedOnInterestedPlaces(userId)
}