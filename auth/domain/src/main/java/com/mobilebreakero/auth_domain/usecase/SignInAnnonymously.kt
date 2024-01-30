package com.mobilebreakero.auth_domain.usecase

import com.mobilebreakero.auth_domain.repo.AuthRepository

class SignInAnnonymously(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() = repo.signInAnonymously()
}