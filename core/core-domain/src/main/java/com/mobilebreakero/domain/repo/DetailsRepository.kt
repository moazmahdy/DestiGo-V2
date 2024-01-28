package com.mobilebreakero.domain.repo

import com.mobilebreakero.domain.model.DetailsResponse
import com.mobilebreakero.domain.model.ReviewItem
import com.mobilebreakero.domain.model.ReviewResponse
import com.mobilebreakero.domain.util.Response

interface DetailsRepository {
    suspend fun getDetails(id: String): Response<DetailsResponse>
    suspend fun getReviews(): List<ReviewItem>
}