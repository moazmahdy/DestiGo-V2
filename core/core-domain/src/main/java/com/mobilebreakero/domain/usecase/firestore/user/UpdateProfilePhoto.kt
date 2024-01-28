package com.mobilebreakero.domain.usecase.firestore.user

import com.mobilebreakero.domain.repo.FireStoreRepository

class UpdateProfilePhoto(
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke(id: String, photoUrl: String) =
        repo.updateUserPhotoUrl(id = id, photoUrl = photoUrl)
}