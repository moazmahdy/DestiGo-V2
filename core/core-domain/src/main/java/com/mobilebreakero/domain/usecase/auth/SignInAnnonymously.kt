package com.mobilebreakero.domain.usecase.auth

class SignInAnnonymously(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() = repo.signInAnonymously()
}