package com.mobilebreakero.ui.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.auth_domain.typealiases.SendEmailVerificationResponse
import com.mobilebreakero.auth_domain.typealiases.SignUpResponse
import com.mobilebreakero.auth_domain.usecase.AuthUseCase
import com.mobilebreakero.auth_domain.util.Response.Loading
import com.mobilebreakero.auth_domain.util.Response.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val useCase: AuthUseCase
) : ViewModel(){

    var signUpResponse by mutableStateOf<SignUpResponse>(Success(false))
        private set

    var sendEmailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(Success(false))
        private set

    fun signUpWithEmailAndPassword(name: String, email: String, password: String) = viewModelScope.launch {
        signUpResponse = Loading
        signUpResponse = useCase.signUpWithEmailAndPassword(name, email, password)
    }

    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = Loading
        sendEmailVerificationResponse = useCase.sendEmailVerification()
    }

}