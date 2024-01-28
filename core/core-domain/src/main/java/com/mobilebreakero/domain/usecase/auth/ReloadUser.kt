package com.mobilebreakero.domain.usecase.auth

class ReloadUser (
    private val repo: AuthRepository
){
    suspend operator fun invoke() = repo.reloadFirebaseUser()
}