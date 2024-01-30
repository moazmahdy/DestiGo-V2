package com.mobilebreakero.auth_domain.usecase

import com.mobilebreakero.auth_domain.repo.AuthRepository


class SignOut(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() = repo.signOut()

}