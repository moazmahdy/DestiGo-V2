package com.mobilebreakero.auth_domain.usecase
import com.mobilebreakero.auth_domain.repo.AuthRepository

class UpdateEmail(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(email: String) = repo.updateEmail(email)
}