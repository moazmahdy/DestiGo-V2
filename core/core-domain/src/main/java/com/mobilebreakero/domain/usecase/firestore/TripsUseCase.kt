package com.mobilebreakero.domain.usecase.firestore

import com.mobilebreakero.domain.usecase.firestore.trips.AddChickList
import com.mobilebreakero.domain.usecase.firestore.trips.AddPlaceVisitDate
import com.mobilebreakero.domain.usecase.firestore.trips.AddPlaces
import com.mobilebreakero.domain.usecase.firestore.trips.AddPublicTrips
import com.mobilebreakero.domain.usecase.firestore.trips.AddTrip
import com.mobilebreakero.domain.usecase.firestore.trips.AddTripJournal
import com.mobilebreakero.domain.usecase.firestore.trips.DeleteTrip
import com.mobilebreakero.domain.usecase.firestore.trips.GetPublicTrips
import com.mobilebreakero.domain.usecase.firestore.trips.GetTripDetails
import com.mobilebreakero.domain.usecase.firestore.trips.GetTrips
import com.mobilebreakero.domain.usecase.firestore.trips.GetTripsByCategories
import com.mobilebreakero.domain.usecase.firestore.trips.IsPlaceVisited
import com.mobilebreakero.domain.usecase.firestore.trips.UpdatePhoto
import com.mobilebreakero.domain.usecase.firestore.trips.UpdatePlacePhoto
import com.mobilebreakero.domain.usecase.firestore.trips.UpdateTripDate
import com.mobilebreakero.domain.usecase.firestore.trips.UpdateTripDays
import com.mobilebreakero.domain.usecase.firestore.trips.UpdateTripName


data class TripsUseCase(
    val addTrip: AddTrip,
    val getTrips: GetTrips,
    val chickList: AddChickList,
    val places: AddPlaces,
    val deleteTrip: DeleteTrip,
    val updatePhoto: UpdatePhoto,
    val getTripDetails: GetTripDetails,
    val getTripsByCategories: GetTripsByCategories,
    val isVisited: IsPlaceVisited,
    val updatePlacePhoto: UpdatePlacePhoto,
    val addPlaceVisitDate: AddPlaceVisitDate,
    val addTripJournal: AddTripJournal,
    val getPublicTrips: GetPublicTrips,
    val savePublicTrips: AddPublicTrips,
    val updateTripDate: UpdateTripDate,
    val updateTripDays: UpdateTripDays,
    val updateTripName: UpdateTripName,
    val isTripFinished: IsTripFinished
)