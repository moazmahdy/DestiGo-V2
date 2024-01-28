package com.mobilebreakero.domain.usecase.firestore.user

import com.mobilebreakero.domain.repo.FireStoreRepository

class UpdateStatus(
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke(id: String, status: String) =
        repo.updateUserStatues(id = id, status = status)
}