package com.mobilebreakero.auth_domain.repo

import com.mobilebreakero.auth_domain.model.PhotoDataItem
import com.mobilebreakero.auth_domain.util.Response

interface PhotoRepository{
    suspend fun getPhotos(locationId: String): Response<List<PhotoDataItem?>>
}