package com.mobilebreakero.profile.yourtrips

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.auth_domain.model.Trip
import com.mobilebreakero.auth_domain.model.TripsItem
import com.mobilebreakero.auth_domain.repo.getPublicTripsResponse
import com.mobilebreakero.auth_domain.repo.getTripsResponse
import com.mobilebreakero.auth_domain.usecase.firestore.TripsUseCase
import com.mobilebreakero.auth_domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YourTripsViewModel @Inject constructor(
    private val tripsUseCase: TripsUseCase
) : ViewModel() {

    val currentUser = Firebase.auth.currentUser?.uid

    init {
        getTrips(userId = currentUser ?: "")
        getPublicTrips(userId = currentUser ?: "")
    }

    private val _tripsFlow = MutableStateFlow<getTripsResponse>(Response.Loading)
    val tripsFlow: StateFlow<getTripsResponse> get() = _tripsFlow

    var tripsResult by mutableStateOf(listOf<Trip>())

    fun getTrips(userId: String) {
        viewModelScope.launch {
            try {
                val result = tripsUseCase.getTrips(userId)
                if (result is Response.Success) {
                    val trips = result.data
                    tripsResult = trips
                    _tripsFlow.value = Response.Success(trips)
                } else {
                    _tripsFlow.value = result
                }
            } catch (e: Exception) {
                _tripsFlow.value = Response.Failure(e)
            }
        }
    }

    private val _publicTripsFlow = MutableStateFlow<getPublicTripsResponse>(Response.Loading)
    val publicTripsFlow: StateFlow<getPublicTripsResponse> get() = _publicTripsFlow

    var publicTripResult by mutableStateOf(listOf<TripsItem>())

    fun getPublicTrips(userId: String) {
        viewModelScope.launch {
            try {
                val result = tripsUseCase.getPublicTrips(userId)
                if (result is Response.Success) {
                    val trips = result.data
                    publicTripResult = trips
                    _publicTripsFlow.value = Response.Success(trips)
                } else {
                    _publicTripsFlow.value = result

                }
            } catch (e: Exception) {
                _publicTripsFlow.value = Response.Failure(e)
            }
        }
    }
}