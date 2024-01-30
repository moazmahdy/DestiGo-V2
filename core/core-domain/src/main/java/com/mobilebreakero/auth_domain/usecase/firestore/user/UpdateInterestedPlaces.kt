package com.mobilebreakero.auth_domain.usecase.firestore.user

import com.mobilebreakero.auth_domain.repo.FireStoreRepository
import javax.inject.Inject

class UpdateInterestedPlaces @Inject constructor(
    private val fireStoreRepository: FireStoreRepository
) {
    suspend operator fun invoke(
        id: String,
        interestedPlaces: List<String>
    ) = fireStoreRepository.updateUserInterestedPlaces(
        id, interestedPlaces
    )
}