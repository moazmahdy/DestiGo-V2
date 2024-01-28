package com.mobilebreakero.domain.usecase.auth

class SendPasswordResetEmail(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(email: String) = repo.sendPasswordResetEmail(email)
}