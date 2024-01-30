package com.mobilebreakero.auth_domain.usecase

import kotlinx.coroutines.CoroutineScope
import com.mobilebreakero.auth_domain.repo.AuthRepository

class GetAuthState(
    private val repo: AuthRepository
){
    operator fun invoke(viewModelScope: CoroutineScope) = repo.getAuthState(viewModelScope)
}