package com.mobilebreakero.auth_domain.usecase
import com.mobilebreakero.auth_domain.repo.AuthRepository

class SignUpWithEmailAndPassword(
    private val repo : AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ) = repo.signUpWithEmailAndPassword(name, email, password)

}