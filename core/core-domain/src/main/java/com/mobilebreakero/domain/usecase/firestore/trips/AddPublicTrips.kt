package com.mobilebreakero.domain.usecase.firestore.trips

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.mobilebreakero.domain.model.TripsItem
import com.mobilebreakero.domain.repo.TripsRepo
import com.mobilebreakero.domain.repo.addTripResponse
import javax.inject.Inject

class AddPublicTrips @Inject constructor(
    private val tripRepo: TripsRepo
) {
    suspend operator fun invoke(
        trip: TripsItem,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ): addTripResponse {
        return tripRepo.savePublicTrip(trip, onSuccessListener, onFailureListener)
    }
}