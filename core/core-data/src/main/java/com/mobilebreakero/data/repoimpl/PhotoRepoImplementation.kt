package com.mobilebreakero.data.repoimpl

import com.mobilebreakero.data.remote.TripApi
import com.mobilebreakero.domain.model.PhotoDataItem
import com.mobilebreakero.domain.repo.PhotoRepository
import com.mobilebreakero.domain.util.Response

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