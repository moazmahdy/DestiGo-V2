package com.mobilebreakero.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.auth_domain.model.DetailsResponse
import com.mobilebreakero.auth_domain.model.PhotoDataItem
import com.mobilebreakero.auth_domain.model.ReviewItem
import com.mobilebreakero.auth_domain.model.Trip
import com.mobilebreakero.auth_domain.model.TripsItem
import com.mobilebreakero.auth_domain.repo.getPublicTripDetailsResponse
import com.mobilebreakero.auth_domain.repo.getTripDetailsResponse
import com.mobilebreakero.auth_domain.repo.updatePlacesResponse
import com.mobilebreakero.auth_domain.repo.updateTripResponse
import com.mobilebreakero.auth_domain.usecase.DetailsUseCase
import com.mobilebreakero.auth_domain.usecase.GetPublicTripsUseCase
import com.mobilebreakero.auth_domain.usecase.GetReviewsUseCase
import com.mobilebreakero.auth_domain.usecase.PhotoUseCase
import com.mobilebreakero.auth_domain.usecase.UpdatePublicTripDate
import com.mobilebreakero.auth_domain.usecase.UpdatePublicTripDays
import com.mobilebreakero.auth_domain.usecase.firestore.TripsUseCase
import com.mobilebreakero.auth_domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val photoUseCase: PhotoUseCase,
    private val detailsUseCase: DetailsUseCase,
    private val tripsUseCase: TripsUseCase,
    private val getPublicTripsUseCase: GetPublicTripsUseCase,
    private val updatePublicTripDays: UpdatePublicTripDays,
    private val updatePublicTripDate: UpdatePublicTripDate,
    private val getReviewsUseCase: GetReviewsUseCase,
) : ViewModel() {

    var addPhotoResponse by mutableStateOf<updateTripResponse>(Response.Success(false))
        private set

    fun addPhoto(photo: String, id: String) {

        viewModelScope.launch {
            try {
                addPhotoResponse = Response.Loading
                addPhotoResponse = tripsUseCase.updatePhoto(photo, id)

            } catch (
                e: Exception
            ) {
                addPhotoResponse =
                    Response.Failure(e)
            }
        }
    }

    private val _photo =
        MutableStateFlow<Response<List<PhotoDataItem?>>>(Response.Success(listOf()))
    val photo: StateFlow<Response<List<PhotoDataItem?>>> = _photo
    var photoResult: Response<List<PhotoDataItem?>> = Response.Success(listOf())

    fun getPhoto(locationId: String) {
        viewModelScope.launch {
            try {
                _photo.value = Response.Loading
                val result = photoUseCase.invoke(locationId)
                _photo.value = result
                photoResult = result
            } catch (e: Exception) {
                _photo.value = Response.Failure(e)
            }
        }
    }

    private val details =
        MutableStateFlow<Response<DetailsResponse>>(Response.Success(DetailsResponse()))

    val detailsResult: StateFlow<Response<DetailsResponse>> = details

    fun getDetails(id: String) {
        viewModelScope.launch {
            try {
                details.value = Response.Loading
                val result = detailsUseCase.invoke(id)
                details.value = result
            } catch (e: Exception) {
                details.value = Response.Failure(e)
            }
        }
    }

    private val tripDetails =
        MutableStateFlow<getTripDetailsResponse>(Response.Success(Trip()))

    val tripDetailsResult: StateFlow<getTripDetailsResponse> = tripDetails

    fun getTripDetailsResult(id: String) {
        viewModelScope.launch {
            try {
                tripDetails.value = Response.Loading
                val result = tripsUseCase.getTripDetails(id)
                tripDetails.value = result
            } catch (e: Exception) {
                tripDetails.value = Response.Failure(e)
            }
        }
    }

    var addPlacePhotoResponse = MutableStateFlow<updatePlacesResponse>(Response.Success(false))
        private set

    fun addPlacePhoto(photo: String, tripId: String) {
        viewModelScope.launch {
            try {
                addPlacePhotoResponse.value = Response.Loading
                val result =
                    tripsUseCase.updatePlacePhoto(photo = photo, id = tripId)
                addPlacePhotoResponse.value = result
            } catch (e: Exception) {
                addPlacePhotoResponse.value = Response.Failure(e)
            }
        }
    }

    var addTripJournalResponse = MutableStateFlow<updatePlacesResponse>(Response.Success(false))
        private set

    fun addTripJournal(
        journal: String,
        journalId: String,
        tripId: String,
        title: String,
        image: String,
        date: String
    ) {
        viewModelScope.launch {
            try {
                addTripJournalResponse.value = Response.Loading

                val result =
                    tripsUseCase.addTripJournal(
                        journal = journal,
                        journalId = journalId,
                        tripId = tripId,
                        title = title,
                        image = image,
                        date = date
                    )
                addTripJournalResponse.value = result

            } catch (e: Exception) {
                addTripJournalResponse.value = Response.Failure(e)
            }
        }
    }

    var updatePlaceDateToVisit = MutableStateFlow<updatePlacesResponse>(Response.Success(false))
        private set

    fun updatePlaceDateToVisit(date: String, id: String, placeId: String) {
        viewModelScope.launch {
            try {
                updatePlaceDateToVisit.value = Response.Loading
                val result =
                    tripsUseCase.addPlaceVisitDate(date = date, id = id, placeId = placeId)
                updatePlaceDateToVisit.value = result
            } catch (e: Exception) {
                updatePlaceDateToVisit.value = Response.Failure(e)
            }
        }
    }

    var updateTripNameResponse = MutableStateFlow<updatePlacesResponse>(Response.Success(false))
        private set

    fun updateTripName(name: String, id: String) {
        viewModelScope.launch {
            try {
                updateTripNameResponse.value = Response.Loading
                val result =
                    tripsUseCase.updateTripName(tripName = name, tripId = id)
                updateTripNameResponse.value = result
            } catch (e: Exception) {
                updateTripNameResponse.value = Response.Failure(e)
            }
        }
    }

    var updateDateResponse = MutableStateFlow<updatePlacesResponse>(Response.Success(false))
        private set

    fun updateTripDate(date: String, id: String) {
        viewModelScope.launch {
            try {
                updateDateResponse.value = Response.Loading
                val result =
                    tripsUseCase.updateTripDate(tripDate = date, tripId = id)
                updateDateResponse.value = result
            } catch (e: Exception) {
                updateDateResponse.value = Response.Failure(e)
            }
        }
    }

    var updateTripDaysResponse = MutableStateFlow<updatePlacesResponse>(Response.Success(false))
        private set

    fun updateTripDays(days: String, id: String) {
        viewModelScope.launch {
            try {
                updateTripDaysResponse.value = Response.Loading
                val result =
                    tripsUseCase.updateTripDays(tripDays = days, tripId = id)
                updateTripDaysResponse.value = result
            } catch (e: Exception) {
                updateTripDaysResponse.value = Response.Failure(e)
            }
        }
    }

    var publicTripDetailsResponse =
        MutableStateFlow<getPublicTripDetailsResponse>(Response.Success(TripsItem()))
        private set

    val publicTripDetailsResult: StateFlow<Response<TripsItem?>> = publicTripDetailsResponse

    fun getPublicTripDetails(id: String) {
        viewModelScope.launch {
            try {
                publicTripDetailsResponse.value = Response.Loading
                val result =
                    getPublicTripsUseCase.invoke(id)
                publicTripDetailsResponse.value = result
            } catch (e: Exception) {
                publicTripDetailsResponse.value = Response.Failure(e)
            }
        }
    }

    var updatePublicTripDateResponse =
        MutableStateFlow<Response<Boolean>>(Response.Success(false))
        private set

    fun updatePublicTripDatevm(startDate: String? = null, endDate: String? = null, id: String) {
        viewModelScope.launch {
            try {
                updatePublicTripDateResponse.value = Response.Loading
                val result =
                    updatePublicTripDate(startDate = startDate, endDate = endDate, tripId = id)
                updatePublicTripDateResponse.value = result
            } catch (e: Exception) {
                updatePublicTripDateResponse.value = Response.Failure(e)
            }
        }
    }

    var updatePublicTripDayResponse =
        MutableStateFlow<Response<Boolean>>(Response.Success(false))
        private set

    fun updatePublicTripDayvm(days: String, id: String) {
        viewModelScope.launch {
            try {
                updatePublicTripDayResponse.value = Response.Loading
                val result = updatePublicTripDays.updatePublicTripDate(
                    days = days,
                    tripId = id
                )
                updatePublicTripDayResponse.value = result
            } catch (e: Exception) {
                updatePublicTripDayResponse.value = Response.Failure(e)
            }
        }
    }


    var getReviews by mutableStateOf(listOf(ReviewItem()))
        private set

    fun getReviews() {
        viewModelScope.launch {
            return@launch try {
                val result = getReviewsUseCase.invoke()
                Log.e("getReviews", result.toString())
                getReviews = result
            } catch (e: Exception) {
                getReviews = emptyList()
            }
        }
    }

}