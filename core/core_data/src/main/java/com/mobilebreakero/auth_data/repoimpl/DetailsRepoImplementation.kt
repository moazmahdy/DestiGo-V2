package com.mobilebreakero.auth_data.repoimpl

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.mobilebreakero.auth_data.remote.TripApi
import com.mobilebreakero.auth_domain.model.DetailsResponse
import com.mobilebreakero.auth_domain.model.ReviewItem
import com.mobilebreakero.auth_domain.model.ReviewResponse
import com.mobilebreakero.auth_domain.repo.DetailsRepository
import com.mobilebreakero.auth_domain.util.Response
import java.io.InputStreamReader

class DetailsRepoImplementation(
    private val api: TripApi,
    private val context: Context
) : DetailsRepository {

    override suspend fun getDetails(id: String): Response<DetailsResponse> {
        val response = api.getDetails(locationId = id)
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Response.Success(body)
            } else {
                Response.Failure(Exception("No data found"))
            }
        } else {
            Response.Failure(Exception("Something went wrong"))
        }
    }

    override suspend fun getReviews(): List<ReviewItem> {
        return try {
            val inputStream = context.assets.open("reviews.json")
            val reader = InputStreamReader(inputStream)
            val reviewsList = Gson().fromJson(reader, ReviewResponse::class.java)
            reviewsList.response as List<ReviewItem>
        } catch (e: Exception) {
            Log.e("DetailsRepoImplementation", "getReviews: ${e.message}")
            emptyList()
        }
    }
}