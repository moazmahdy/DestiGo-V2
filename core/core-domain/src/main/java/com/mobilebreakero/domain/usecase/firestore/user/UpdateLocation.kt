package com.mobilebreakero.domain.usecase.firestore.user

import com.mobilebreakero.domain.repo.FireStoreRepository

class UpdateLocation(
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke(id: String, location: String) =
        repo.updateUserLocation(id = id, location = location)
}