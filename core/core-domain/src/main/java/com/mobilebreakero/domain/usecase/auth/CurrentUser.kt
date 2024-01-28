package com.mobilebreakero.domain.usecase.auth

class CurrentUser (
    private val repo : AuthRepository,
){
    operator fun invoke() = repo.currentUser
}