package com.mobilebreakero.profile.account.accountacess.updateusername

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.auth_domain.repo.updateUserResponse
import com.mobilebreakero.auth_domain.usecase.firestore.UserUseCase
import com.mobilebreakero.auth_domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateUserNameViewModel @Inject constructor(
    private val useCase: UserUseCase
) : ViewModel() {

    var updateUserNameResponse by mutableStateOf<updateUserResponse>(Response.Success(false))
        private set

    fun updateUserName(id: String, userName: String) {
        viewModelScope.launch {
            updateUserNameResponse = Response.Loading
            updateUserNameResponse = useCase.updateUser(id, userName)
        }
    }
}