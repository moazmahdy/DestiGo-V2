package com.mobilebreakero.auth_domain.usecase.firestore.user

import com.mobilebreakero.auth_domain.repo.FireStoreRepository

class UpdateLocation(
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke(id: String, location: String) =
        repo.updateUserLocation(id = id, location = location)
}