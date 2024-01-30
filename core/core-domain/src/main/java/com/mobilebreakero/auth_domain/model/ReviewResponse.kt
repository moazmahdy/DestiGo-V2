package com.mobilebreakero.auth_domain.model

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("response")
    val response: List<ReviewItem?>? = null
)

data class ReviewItem(
    @SerializedName("User")
    val user: String? = null,
    @SerializedName("Location")
    val location: String? = null,
    @SerializedName("DateofReview")
    val dateofReview: String? = null,
    @SerializedName("ReviewTitle")
    val reviewTitle: String? = null,
    @SerializedName("Review")
    val review: String? = null
)
