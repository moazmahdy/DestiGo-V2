package com.mobilebreakero.auth_data.repoimpl

import com.mobilebreakero.auth_data.remote.TripApi
import com.mobilebreakero.auth_domain.model.PhotoDataItem
import com.mobilebreakero.auth_domain.repo.PhotoRepository
import com.mobilebreakero.auth_domain.util.Response

class PhotoRepoImplementation(private val api: TripApi) : PhotoRepository {

    override suspend fun getPhotos(locationId: String): Response<List<PhotoDataItem?>> {
        return try {
            val response = api.getPhotos(locationId)
            if (response.isSuccessful) {
                Response.Success(response.body()!!.data)
            } else {
                Response.Failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}