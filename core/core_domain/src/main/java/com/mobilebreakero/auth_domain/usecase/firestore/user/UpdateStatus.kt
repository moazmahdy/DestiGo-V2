package com.mobilebreakero.auth_domain.usecase.firestore.user

import com.mobilebreakero.auth_domain.repo.FireStoreRepository

class UpdateStatus(
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke(id: String, status: String) =
        repo.updateUserStatues(id = id, status = status)
}