package com.mobilebreakero.auth_domain.usecase

import com.mobilebreakero.auth_domain.repo.AuthRepository

class RestPassword (
    private val repo: AuthRepository
){
    suspend operator fun invoke(email: String, confirmationCode: Int) = repo.sendResetPassword(
        email,
        confirmationCode
    )
}