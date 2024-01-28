package com.mobilebreakero.interestedplaces

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.repo.updateUserResponse
import com.mobilebreakero.domain.usecase.firestore.UserUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestedPlacesViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) :
    ViewModel() {

    var updateUserResponse = mutableStateOf<updateUserResponse>(Response.Success(false))
        private set

    fun addInterestedPlacesIntoDatasource(id: String, interestedPlaces: List<String>) {
        viewModelScope.launch {
            try {
                updateUserResponse.value = Response.Loading
                updateUserResponse.value =
                    userUseCase.updateUserInterestedPlaces(id, interestedPlaces)
            } catch (e: Exception) {
                updateUserResponse.value = Response.Failure(e)
            }
        }
    }

}