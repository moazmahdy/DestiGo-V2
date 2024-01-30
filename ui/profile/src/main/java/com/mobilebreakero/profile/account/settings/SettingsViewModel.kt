package com.mobilebreakero.profile.account.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.auth_domain.typealiases.SignOutResponse
import com.mobilebreakero.auth_domain.usecase.AuthUseCase
import com.mobilebreakero.auth_domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val useCase: AuthUseCase) : ViewModel() {

    var signOutResponse by mutableStateOf<SignOutResponse>(Response.Success(false))
        private set

    fun signOut() {
        viewModelScope.launch {
            signOutResponse = Response.Loading
            signOutResponse = useCase.signOut()
        }
    }

}