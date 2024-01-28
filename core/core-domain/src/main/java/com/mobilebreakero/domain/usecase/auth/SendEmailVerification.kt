package com.mobilebreakero.domain.usecase.auth

class SendEmailVerification(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() = repo.sendEmailVerification()
}