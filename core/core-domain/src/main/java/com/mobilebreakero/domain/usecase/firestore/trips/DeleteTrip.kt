package com.mobilebreakero.domain.usecase.firestore.trips

import com.mobilebreakero.domain.repo.TripsRepo
import javax.inject.Inject

class DeleteTrip @Inject constructor(
    private val repo : TripsRepo
){
    suspend operator fun invoke(
        id: String
    ) = repo.deleteTrip(id)
}
