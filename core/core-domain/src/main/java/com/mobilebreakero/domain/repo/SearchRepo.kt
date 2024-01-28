package com.mobilebreakero.domain.repo

import com.mobilebreakero.domain.model.DataItem
import com.mobilebreakero.domain.util.Response

interface SearchResultRepo {

    suspend fun searchPlaces(
        query: String,
        language: String,
        filter: String
    ): Response<List<DataItem?>>
}