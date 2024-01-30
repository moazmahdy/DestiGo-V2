package com.mobilebreakero.auth_domain.usecase.firestore.trips

import com.mobilebreakero.auth_domain.repo.TripsRepo
import com.mobilebreakero.auth_domain.repo.updatePlacesResponse
import javax.inject.Inject

class AddTripJournal @Inject constructor(
    private val repo: TripsRepo
) {
    suspend operator fun invoke(
        journal: String,
        journalId: String,
        tripId: String,
        title: String,
        image: String,
        date: String
    ): updatePlacesResponse =
        repo.addTripJournal(
            journal = journal,
            journalId = journalId,
            tripId = tripId,
            title = title,
            image = image,
            date = date
        )

}