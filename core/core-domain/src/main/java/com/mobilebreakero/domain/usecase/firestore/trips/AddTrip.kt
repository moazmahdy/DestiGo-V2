package com.mobilebreakero.domain.usecase.firestore.trips

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.repo.TripsRepo
import javax.inject.Inject

class AddTrip @Inject constructor(
    private val repo: TripsRepo
) {
    suspend operator fun invoke(
        trip: Trip,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ) =
        repo.addTrip(trip, onSuccessListener, onFailureListener)
}