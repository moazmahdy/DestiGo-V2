package com.mobilebreakero.domain.usecase.auth

class SignInWithEmailAndPassword(
    private val repo : AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) = repo.signInWithEmailAndPassword(email, password)

}