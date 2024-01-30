package com.mobilebreakero.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.details.components.DetailsContent
import com.mobilebreakero.auth_domain.model.DetailsResponse
import com.mobilebreakero.auth_domain.model.PhotoDataItem
import com.mobilebreakero.auth_domain.util.Response

@Composable
fun DetailsScreen(
    locationId: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = locationId) {
        viewModel.getPhoto(locationId)
        viewModel.getDetails(locationId)
        viewModel.getReviews()
    }

    val photos by viewModel.photo.collectAsState()
    val details by viewModel.detailsResult.collectAsState()

    when (photos) {
        is Response.Success -> {
            val results = (photos as Response.Success<List<PhotoDataItem?>>).data
            when (details) {
                is Response.Success -> {
                    val detailsResponse =
                        (details as Response.Success<DetailsResponse>).data
                    val reviewsResponse = viewModel.getReviews

                    DetailsContent(
                        photos = results,
                        detailsResponse = detailsResponse,
                        reviewsResponse
                    )
                }

                is Response.Failure -> {

                }

                else -> {
                    Response.Loading
                    LoadingIndicator()
                }
            }
        }

        is Response.Failure -> {

        }

        else -> {
            Response.Loading
            LoadingIndicator()
        }
    }

}