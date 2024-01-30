package com.mobilebreakero.auth_domain.repo

import com.mobilebreakero.auth_domain.model.DetailsResponse
import com.mobilebreakero.auth_domain.model.ReviewItem
import com.mobilebreakero.auth_domain.util.Response

interface DetailsRepository {
    suspend fun getDetails(id: String): Response<DetailsResponse>
    suspend fun getReviews(): List<ReviewItem>
}