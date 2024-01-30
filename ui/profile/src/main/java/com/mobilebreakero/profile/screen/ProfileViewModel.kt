package com.mobilebreakero.profile.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.auth_domain.model.TripsItem
import com.mobilebreakero.auth_domain.repo.getPublicTripsResponse
import com.mobilebreakero.auth_domain.repo.savedPlacesResponse
import com.mobilebreakero.auth_domain.usecase.firestore.UserUseCase
import com.mobilebreakero.auth_domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: UserUseCase
) : ViewModel() {

    private val _savedPlacesFlow = MutableStateFlow<savedPlacesResponse>(Response.Loading)
    val savedPlaces: StateFlow<savedPlacesResponse> get() = _savedPlacesFlow

    fun getSavedPlaces(userId: String) {
        viewModelScope.launch {
            try {
                val result = useCase.getSavedPlaces(userId)
                if (result is Response.Success) {
                    val savedPlaces = result.data
                    _savedPlacesFlow.value = Response.Success(savedPlaces)
                } else {
                    _savedPlacesFlow.value = result
                }
            } catch (e: Exception) {
                _savedPlacesFlow.value = Response.Failure(e)
            }
        }
    }


    private val _publicTripsFlow = MutableStateFlow<getPublicTripsResponse>(Response.Loading)
    val publicTripsFlow: StateFlow<getPublicTripsResponse> get() = _publicTripsFlow

    var publicTripResult by mutableStateOf(listOf<TripsItem>())

    fun getSavedTrips(userId: String) {

        viewModelScope.launch {
            try {
                val result = useCase.getSavedTrips(userId)
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