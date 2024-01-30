package com.mobilebreakero.auth_domain.usecase
import com.mobilebreakero.auth_domain.repo.AuthRepository

class CurrentUser (
    private val repo : AuthRepository,
){
    operator fun invoke() = repo.currentUser
}