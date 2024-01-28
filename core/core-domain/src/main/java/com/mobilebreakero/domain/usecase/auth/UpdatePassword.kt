package com.mobilebreakero.domain.usecase.auth

class UpdatePassword (
    private val repo: AuthRepository
){
    suspend operator fun invoke(password: String) = repo.resetPassword(password)
}