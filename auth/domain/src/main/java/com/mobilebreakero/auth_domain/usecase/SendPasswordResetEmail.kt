package com.mobilebreakero.auth_domain.usecase
import com.mobilebreakero.auth_domain.repo.AuthRepository

class SendPasswordResetEmail(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(email: String) = repo.sendPasswordResetEmail(email)
}