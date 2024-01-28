package com.mobilebreakero.domain.usecase

import com.mobilebreakero.domain.model.DataItem
import com.mobilebreakero.domain.repo.SearchResultRepo
import com.mobilebreakero.domain.util.Response

class SearchPlacesUseCase(
    private val searchResultRepo: SearchResultRepo
) {
    suspend operator fun invoke(
        query: String,
        language: String,
        filter: String
    ): Response<List<DataItem?>> =
        searchResultRepo.searchPlaces(query = query, language = language, filter = filter)
}