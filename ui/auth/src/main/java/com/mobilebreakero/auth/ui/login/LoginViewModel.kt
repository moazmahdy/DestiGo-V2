package com.mobilebreakero.auth.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.auth_domain.typealiases.SendPasswordResetEmailResponse
import com.mobilebreakero.auth_domain.typealiases.SignInResponse
import com.mobilebreakero.auth_domain.usecase.AuthUseCase
import com.mobilebreakero.auth_domain.util.Response.Success
import com.mobilebreakero.auth_domain.util.Response.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: AuthUseCase
) : ViewModel() {

    var signInResponse by mutableStateOf<SignInResponse>(Success(false))
        private set

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signInResponse = Loading
        signInResponse = useCase.signInWithEmailAndPassword(email, password)
    }

    var forgetPasswordEmailResponse by mutableStateOf<SendPasswordResetEmailResponse>(Success(false))
        private set

    fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        forgetPasswordEmailResponse = Loading
        forgetPasswordEmailResponse = useCase.sendPasswordResetEmail(email)
    }
}