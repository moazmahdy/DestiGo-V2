package com.mobilebreakero.domain.usecase.firestore.trips

import com.mobilebreakero.domain.repo.TripsRepo
import javax.inject.Inject

class AddChickList @Inject constructor(
    private val repo: TripsRepo
) {
    suspend operator fun invoke(
        itemName: String,
        id: String,
        checked: Boolean,
        checkItemId: String
    ) =
        repo.addCheckList(
            itemName = itemName,
            id = id,
            checked = checked,
            checkItemId = checkItemId
        )

}