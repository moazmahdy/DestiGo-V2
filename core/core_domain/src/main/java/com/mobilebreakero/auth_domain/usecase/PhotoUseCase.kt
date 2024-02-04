package com.mobilebreakero.auth_domain.usecase

import com.mobilebreakero.auth_domain.model.PhotoDataItem
import com.mobilebreakero.auth_domain.repo.PhotoRepository
import com.mobilebreakero.auth_domain.util.Response
import javax.inject.Inject

class PhotoUseCase @Inject constructor(private val repo: PhotoRepository) {
    suspend operator fun invoke(locationId: String): Response<List<PhotoDataItem?>> = repo.getPhotos(locationId)
}