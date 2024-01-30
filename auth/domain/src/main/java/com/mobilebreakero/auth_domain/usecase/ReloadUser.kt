package com.mobilebreakero.auth_domain.usecase
import com.mobilebreakero.auth_domain.repo.AuthRepository

class ReloadUser (
    private val repo: AuthRepository
){
    suspend operator fun invoke() = repo.reloadFirebaseUser()
}