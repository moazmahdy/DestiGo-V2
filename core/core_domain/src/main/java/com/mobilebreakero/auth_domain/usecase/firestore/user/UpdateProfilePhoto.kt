package com.mobilebreakero.auth_domain.usecase.firestore.user

import com.mobilebreakero.auth_domain.repo.FireStoreRepository

class UpdateProfilePhoto(
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke(id: String, photoUrl: String) =
        repo.updateUserPhotoUrl(id = id, photoUrl = photoUrl)
}