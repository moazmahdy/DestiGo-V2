package com.mobilebreakero.domain.usecase.firestore

import com.mobilebreakero.domain.repo.FireStoreRepository
import javax.inject.Inject

class GetSavedPlaces @Inject constructor(
    private val fireStoreRepository: FireStoreRepository
) {
    suspend operator fun invoke(userId: String) = fireStoreRepository.getUserSavedPlaces(userId)
}

class GetSavedTrips @Inject constructor(
    private val fireStoreRepository: FireStoreRepository
) {
    suspend operator fun invoke(userId: String) = fireStoreRepository.getUserSavedTrips(userId)
}