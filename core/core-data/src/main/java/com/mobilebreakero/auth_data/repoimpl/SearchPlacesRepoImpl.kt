package com.mobilebreakero.auth_data.repoimpl

import com.mobilebreakero.auth_data.remote.TripApi
import com.mobilebreakero.auth_domain.model.DataItem
import com.mobilebreakero.auth_domain.repo.SearchResultRepo
import com.mobilebreakero.auth_domain.util.Response

class SearchPlacesRepoImpl(private val api: TripApi) : SearchResultRepo {

    override suspend fun searchPlaces(
        query: String,
        language: String,
        filter: String
    ): Response<List<DataItem?>> {
        return try {
            val response = api.searchLocation(query = query, language = language, filter = filter)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Response.Success(body.data)
                } else {
                    Response.Failure(Exception("No data found"))
                }
            } else {
                Response.Failure(Exception("Something went wrong"))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}