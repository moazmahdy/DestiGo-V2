package com.mobilebreakero.auth.ui.common.components

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.auth_domain.model.Trip
import com.mobilebreakero.auth_domain.repo.getTripsResponse
import com.mobilebreakero.auth_domain.repo.updateTripResponse
import com.mobilebreakero.auth_domain.typealiases.ReloadUserResponse
import com.mobilebreakero.auth_domain.typealiases.SignOutResponse
import com.mobilebreakero.auth_domain.usecase.AuthUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.TripsUseCase
import com.mobilebreakero.auth_domain.util.Response
import com.mobilebreakero.auth_domain.util.Response.Success
import com.mobilebreakero.auth_domain.util.Response.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: AuthUseCase,
    private val tripsUseCase: TripsUseCase,
) : ViewModel() {

    val currentUser = useCase.currentUser.invoke()?.uid

    init {
        getAuthState()
        getTrips(userId = currentUser ?: "")
    }

    fun getAuthState() = useCase.getAuthState.invoke(viewModelScope)

    var reloadUserResponse by mutableStateOf<ReloadUserResponse>(Success(false))
        private set

    var signOutResponse by mutableStateOf<SignOutResponse>(Success(false))
        private set

    fun signOut() = viewModelScope.launch {
        signOutResponse = Loading
        signOutResponse = useCase.signOut.invoke()
    }

    val isEmailVerified get() = useCase.currentUser.invoke()?.isEmailVerified ?: false

    fun reloadUser() = viewModelScope.launch {
        reloadUserResponse = Loading
        reloadUserResponse = useCase.reloadUser()
    }

    var updateTripStatusResponse by mutableStateOf<updateTripResponse>(Success(false))
        private set

    fun isTripFinished(id: String, isFinished: Boolean) {
        viewModelScope.launch {
            try {
                updateTripStatusResponse = Loading
                updateTripStatusResponse = tripsUseCase.isTripFinished(id, isFinished)
            } catch (e: Exception) {
                updateTripStatusResponse = Response.Failure(e)
            }
        }
    }


    private val _tripsFlow = MutableStateFlow<getTripsResponse>(Loading)

    var tripsResult by mutableStateOf(listOf<Trip>())

    fun getTrips(userId: String) {
        viewModelScope.launch {
            try {
                val result = tripsUseCase.getTrips(userId)
                if (result is Success) {
                    val trips = result.data
                    tripsResult = trips
                    _tripsFlow.value = Success(trips)
                    Log.e("MainVM", "getTrips success: $trips")
                } else {
                    _tripsFlow.value = result
                    Log.e("MainVM", "getTrips loading: $result")
                }
            } catch (e: Exception) {
                _tripsFlow.value = Response.Failure(e)
                Log.e("TripsViewModel", "getTrips error: $e")
            }
        }
    }

}