package com.mobilebreakero.domain.usecase.auth

import android.content.Context

class CheckUserSignedInUseCase(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, context: Context) =
        repo.checkUserSignedIn(email, password, context)
}