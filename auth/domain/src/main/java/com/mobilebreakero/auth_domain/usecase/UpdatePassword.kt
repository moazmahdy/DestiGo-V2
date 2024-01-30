package com.mobilebreakero.auth_domain.usecase

import com.mobilebreakero.auth_domain.repo.AuthRepository

class UpdatePassword(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(password: String) = repo.resetPassword(password)
}