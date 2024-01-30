package com.mobilebreakero.details

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.details.components.AmenitiesCard
import com.mobilebreakero.details.components.DetailsCard
import com.mobilebreakero.details.components.ItemsChip
import com.mobilebreakero.details.components.ReviewItemCard
import com.mobilebreakero.details.components.ShowDatePickerDialog
import com.mobilebreakero.auth_domain.model.DetailsResponse
import com.mobilebreakero.auth_domain.model.PhotoDataItem
import com.mobilebreakero.auth_domain.model.ReviewItem
import com.mobilebreakero.auth_domain.model.Trip
import com.mobilebreakero.auth_domain.util.Response


@Composable
fun PlacesDetailsTrips(
    locationId: String,
    tripId: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = locationId) {
        viewModel.getPhoto(locationId)
        viewModel.getDetails(locationId)
        viewModel.getTripDetailsResult(tripId)
        viewModel.getReviews()
    }

    val photos by viewModel.photo.collectAsState()
    val details by viewModel.detailsResult.collectAsState()

    when (photos) {
        is Response.Success -> {
            val resultsPhotos = (photos as Response.Success<List<PhotoDataItem?>>).data
            when (details) {
                is Response.Success -> {
                    val detailsResponse =
                        (details as Response.Success<DetailsResponse>).data
                    val tripDetails by viewModel.tripDetailsResult.collectAsState()

                    when (tripDetails) {
                        is Response.Success -> {
                            val tripsResults = (tripDetails as Response.Success<Trip>).data
                            val reviewsResponse = viewModel.getReviews

                            PlacesTripDetailsContent(
                                photos = resultsPhotos,
                                detailsResponse = detailsResponse,
                                trip = tripsResults,
                                reviewResponse = reviewsResponse
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

        is Response.Failure -> {

        }

        else -> {
            Response.Loading
            LoadingIndicator()
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlacesTripDetailsContent(
    photos: List<PhotoDataItem?>,
    detailsResponse: DetailsResponse,
    viewModel: DetailsViewModel = hiltViewModel(),
    reviewResponse: List<ReviewItem>? = null,
    trip: Trip
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        photos.size
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
            ) { page ->
                val photo = photos[page]?.images?.large?.url
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = photo,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        loading = { LoadingIndicator() })
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.2f))
                    )
                }
            }

            Row(
                Modifier
                    .height(24.dp)
                    .padding(start = 4.dp)
                    .width(100.dp)
                    .align(Alignment.BottomStart),
                horizontalArrangement = Arrangement.Start
            ) {
                repeat(photos.size) { iteration ->
                    val lineWeight = animateFloatAsState(
                        targetValue = if (pagerState.currentPage == iteration) {
                            1.0f
                        } else {
                            if (iteration < pagerState.currentPage) {
                                0.5f
                            } else {
                                0.5f
                            }
                        }, label = "size", animationSpec = tween(300, easing = EaseInOut)
                    )
                    val color =
                        if (pagerState.currentPage == iteration) Color(0xFF4F80FF) else Color.White
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(color)
                            .weight(lineWeight.value)
                            .height(10.dp)
                    )
                }
            }
        }

        Text(
            text = detailsResponse.name ?: "",
            fontSize = 22.sp,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = detailsResponse.rankingData?.rankingString ?: "",
            fontSize = 16.sp,
            modifier = Modifier.padding(3.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        DetailsCard(
            title = "Location Details",
            details = detailsResponse.addressObj?.addressString ?: ""
        ) {
            Spacer(modifier = Modifier.height(20.dp))
        }

        DetailsCard(title = "About", details = detailsResponse.description ?: "") {
            Spacer(modifier = Modifier.height(20.dp))
        }

        if (detailsResponse.amenities?.isNotEmpty() == true)
            AmenitiesCard(title = "Amenities", details = detailsResponse.amenities ?: listOf("")) {
                Spacer(modifier = Modifier.height(20.dp))
            } else
            Spacer(modifier = Modifier.height(20.dp))


        val isDateClicked = remember { mutableStateOf(false) }

        val selectedDate = remember { mutableStateOf(trip.places?.get(0)?.date) }
        DetailsCard(title = "Schedule visit date", details = "") {
            ItemsChip(title = selectedDate.value ?: "") {
                isDateClicked.value = true
            }

        }

        val numberOfReviews = 15
        val randomReviews = reviewResponse?.shuffled()?.take(numberOfReviews)

        Text(text = "Reviews", fontSize = 18.sp, modifier = Modifier.padding(8.dp))

        LazyColumn(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(randomReviews?.size ?: 0) { index ->
                randomReviews?.get(index)?.user?.let {
                    randomReviews[index].review?.let { it1 ->
                        randomReviews[index].dateofReview?.let { it2 ->
                            ReviewItemCard(
                                it,
                                it1,
                                it2
                            )
                        }
                    }
                }
            }
        }


        if (isDateClicked.value) {
            ShowDatePickerDialog(
                selectedDate = selectedDate.value ?: "",
                onDateSelected = { date ->
                    trip.id?.let {
                        trip.places?.get(0)?.id?.let { it1 ->
                            selectedDate.value = date
                        }
                    }
                },
            )
        }

    }
}