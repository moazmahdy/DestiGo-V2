package com.mobilebreakero.domain.usecase.firestore.user

import com.mobilebreakero.domain.repo.FireStoreRepository

class UpdateUser(
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke(id: String, name: String) =
        repo.updateUser(id = id, name = name)
}