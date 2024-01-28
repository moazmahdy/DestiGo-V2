package com.mobilebreakero.domain.usecase.auth

class UpdateEmail(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(email: String) = repo.updateEmail(email)
}