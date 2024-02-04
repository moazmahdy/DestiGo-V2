package com.mobilebreakero.auth_domain.usecase.firestore.trips

import com.mobilebreakero.auth_domain.repo.TripsRepo
import javax.inject.Inject

class UpdatePlacePhoto @Inject constructor(
    private val repo: TripsRepo
) {
    suspend operator fun invoke(id: String, photo: String) =
        repo.updatePhotoPlace(photo, id)
}