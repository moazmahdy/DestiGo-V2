package com.mobilebreakero.domain.usecase.auth

class SignOut(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() = repo.signOut()

}