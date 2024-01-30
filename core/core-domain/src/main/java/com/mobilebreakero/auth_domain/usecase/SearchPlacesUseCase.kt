package com.mobilebreakero.auth_domain.usecase

import com.mobilebreakero.auth_domain.model.DataItem
import com.mobilebreakero.auth_domain.repo.SearchResultRepo
import com.mobilebreakero.auth_domain.util.Response

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