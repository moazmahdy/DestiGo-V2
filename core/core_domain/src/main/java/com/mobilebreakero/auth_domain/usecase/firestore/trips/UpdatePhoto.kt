package com.mobilebreakero.auth_domain.usecase.firestore.trips

import com.mobilebreakero.auth_domain.repo.TripsRepo
import javax.inject.Inject

class UpdatePhoto @Inject constructor(
    private val repo: TripsRepo
) {
    suspend operator fun invoke(
        photo: String,
        id: String
    ) = repo.updatePhoto(photo, id)
}
