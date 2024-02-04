package com.mobilebreakero.auth_domain.repo

import com.mobilebreakero.auth_domain.model.DataItem
import com.mobilebreakero.auth_domain.util.Response

interface SearchResultRepo {

    suspend fun searchPlaces(
        query: String,
        language: String,
        filter: String
    ): Response<List<DataItem?>>
}