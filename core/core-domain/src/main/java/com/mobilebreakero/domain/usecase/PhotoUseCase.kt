package com.mobilebreakero.domain.usecase

import com.mobilebreakero.domain.model.PhotoDataItem
import com.mobilebreakero.domain.repo.PhotoRepository
import com.mobilebreakero.domain.util.Response
import javax.inject.Inject

class PhotoUseCase @Inject constructor(private val repo: PhotoRepository) {
    suspend operator fun invoke(locationId: String): Response<List<PhotoDataItem?>> = repo.getPhotos(locationId)
}