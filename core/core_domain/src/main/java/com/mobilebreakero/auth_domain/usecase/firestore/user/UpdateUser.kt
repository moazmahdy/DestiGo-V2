package com.mobilebreakero.auth_domain.usecase.firestore.user

import com.mobilebreakero.auth_domain.repo.FireStoreRepository

class UpdateUser(
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke(id: String, name: String) =
        repo.updateUser(id = id, name = name)
}