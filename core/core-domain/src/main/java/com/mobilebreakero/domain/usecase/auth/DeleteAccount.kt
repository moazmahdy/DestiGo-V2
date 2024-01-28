package com.mobilebreakero.domain.usecase.auth

class DeleteAccount(private val repo: AuthRepository) {
    suspend operator fun invoke() = repo.deleteAccount()
}