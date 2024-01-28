package com.mobilebreakero.domain.repo

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.model.TripJournal
import com.mobilebreakero.domain.model.TripsItem
import com.mobilebreakero.domain.util.Response

typealias addTripResponse = Response<Boolean>
typealias updateTripResponse = Response<Boolean>
typealias updatePlacesResponse = Response<Boolean>
typealias getTripsResponse = Response<List<Trip>>
typealias getPublicTripsResponse = Response<List<TripsItem>>
typealias getTripDetailsResponse = Response<Trip>
typealias getPublicTripDetailsResponse = Response<TripsItem?>
typealias getTripJournalDetailsResponse = Response<TripJournal?>


interface TripsRepo {

    suspend fun getTrips(id: String): getTripsResponse

    suspend fun addTrip(
        trip: Trip, onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ): addTripResponse

    suspend fun addCheckList(
        itemName: String,
        id: String,
        checked: Boolean = false,
        checkItemId: String
    ): updateTripResponse

    suspend fun addPlaces(
        placeName: String, placeId: String, id: String, placeTripId: String
    ): updatePlacesResponse

    fun updatePhoto(photo: String, id: String): updatePlacesResponse

    fun deleteTrip(id: String): updatePlacesResponse

    suspend fun getTripDetails(id: String): getTripDetailsResponse

    suspend fun getTripsByCategory(categories: String): getTripsResponse

    suspend fun updatePhotoPlace(
        photo: String, id: String
    ): updatePlacesResponse

    suspend fun isPlaceVisited(
        isVisited: Boolean,
        placeId: String,
        tripId: String
    ): updatePlacesResponse

    suspend fun addPlaceVisitDate(
        date: String,
        placeId: String,
        tripId: String
    ): updatePlacesResponse

    suspend fun addTripJournal(
        journal: String,
        journalId: String,
        tripId: String,
        title: String,
        image: String,
        date: String
    ): updatePlacesResponse

    suspend fun savePublicTrip(
        trip: TripsItem,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ): addTripResponse

    suspend fun getPublicTrips(userId: String): getPublicTripsResponse

    suspend fun updateTripName(
        tripName: String,
        tripId: String
    ): updateTripResponse

    suspend fun updateTripDate(
        tripDate: String,
        tripId: String
    ): updateTripResponse

    suspend fun updateTripDays(
        tripDays: String,
        tripId: String
    ): updateTripResponse

    suspend fun isTripFinished(tripId: String, finished: Boolean): updateTripResponse

    suspend fun getTripJournalDetails(
        tripId: String,
        journalId: String
    ): getTripJournalDetailsResponse
}