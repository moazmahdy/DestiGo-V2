package com.mobilebreakero.domain.repo

import com.mobilebreakero.domain.model.PhotoDataItem
import com.mobilebreakero.domain.util.Response

interface PhotoRepository{
    suspend fun getPhotos(locationId: String): Response<List<PhotoDataItem?>>
}