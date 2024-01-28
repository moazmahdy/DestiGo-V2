package com.mobilebreakero.domain.usecase.firestore.user

import com.mobilebreakero.domain.repo.FireStoreRepository
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