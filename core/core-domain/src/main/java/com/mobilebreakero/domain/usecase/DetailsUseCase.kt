package com.mobilebreakero.domain.usecase

import com.mobilebreakero.domain.model.ReviewItem
import com.mobilebreakero.domain.repo.DetailsRepository
import javax.inject.Inject

class DetailsUseCase @Inject constructor(
    private val detailsRepository: DetailsRepository
) {
    suspend operator fun invoke(id: String) = detailsRepository.getDetails(id)
}

class GetReviewsUseCase @Inject constructor(
    private val detailsRepository: DetailsRepository
) {
    suspend operator fun invoke(): List<ReviewItem> = detailsRepository.getReviews()
}