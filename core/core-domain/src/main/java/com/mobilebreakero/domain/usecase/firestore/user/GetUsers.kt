package com.mobilebreakero.domain.usecase.firestore.user

import com.mobilebreakero.domain.repo.FireStoreRepository

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