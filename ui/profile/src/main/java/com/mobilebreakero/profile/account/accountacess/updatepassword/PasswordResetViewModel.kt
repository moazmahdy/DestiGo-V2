package com.mobilebreakero.profile.account.accountacess.updatepassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.auth_domain.typealiases.ResetPasswordResponse
import com.mobilebreakero.auth_domain.typealiases.SendResetPasswordResponse
import com.mobilebreakero.auth_domain.usecase.AuthUseCase
import com.mobilebreakero.auth_domain.util.Response.Loading
import com.mobilebreakero.auth_domain.util.Response.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordResetViewModel @Inject constructor(private val useCase: AuthUseCase) : ViewModel() {

    var sendPasswordResetEmailResponse by mutableStateOf<SendResetPasswordResponse>(Success(false))
        private set

    var updatePasswordResponse by mutableStateOf<ResetPasswordResponse>(Success(false))
        private set
    fun sendResetPasswordEmail(email: String, confirmationCode: Int) = viewModelScope.launch(
        Dispatchers.IO
    ) {
        sendPasswordResetEmailResponse = Loading
        sendPasswordResetEmailResponse = useCase.resetPassword(email, confirmationCode)
    }

    fun updatePassword(password: String) = viewModelScope.launch(Dispatchers.IO) {
        updatePasswordResponse = Loading
        updatePasswordResponse = useCase.updatePassword(password)
    }

}