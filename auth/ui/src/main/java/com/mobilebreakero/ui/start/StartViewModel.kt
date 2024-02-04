package com.mobilebreakero.ui.start

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.auth_domain.typealiases.SignInResponse
import com.mobilebreakero.auth_domain.usecase.AuthUseCase
import com.mobilebreakero.auth_domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val useCase: AuthUseCase
) : ViewModel(){

    var signInResponse by mutableStateOf<SignInResponse>(Response.Success(false))
        private set

    init {
        getAuthState()
    }

    fun getAuthState() = useCase.getAuthState(viewModelScope)

    fun signInAnnonymously() = viewModelScope.launch {
        signInResponse = Response.Loading
        signInResponse = useCase.signInAnonymously()
    }
}