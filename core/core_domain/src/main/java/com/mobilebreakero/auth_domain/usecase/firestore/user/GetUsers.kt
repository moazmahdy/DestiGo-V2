package com.mobilebreakero.auth_domain.usecase.firestore.user

import com.mobilebreakero.auth_domain.repo.FireStoreRepository

class GetUsers (
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke() =
        repo.getUsers()
}

class GetUserById (
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke(id: String) =
        repo.getUserById(id)
}