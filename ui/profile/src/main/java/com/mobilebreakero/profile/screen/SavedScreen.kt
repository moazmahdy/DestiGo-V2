package com.mobilebreakero.profile.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.CoilImage
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.model.RecommendedPlaceItem
import com.mobilebreakero.domain.model.TripsItem
import com.mobilebreakero.domain.util.Response


@Composable
fun SavedScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController
) {
    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser
    val scrollState = rememberScrollState()

    GetUserFromFireStore(
        id = firebaseUser?.uid ?: "",
        user = { userId ->
            userId.id = firebaseUser?.uid
            user.value = userId
        }
    )

    val savedPlaces by viewModel.savedPlaces.collectAsState()
    val publicTrips by viewModel.publicTripsFlow.collectAsState()

    viewModel.getSavedTrips(userId = user.value.id ?: "")
    viewModel.getSavedPlaces(userId = user.value.id ?: "")

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        Text(
            text = "Here's your saved places!",
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 10.dp, bottom = 6.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        LazyRow {
            when (savedPlaces) {
                is Response.Loading -> {
                    items(1) {
                        LoadingIndicator()
                    }
                }

                is Response.Success -> {
                    val places = (savedPlaces as Response.Success<List<RecommendedPlaceItem>>).data

                    items(places.size) { index ->
                        TripItem(
                            imageUri = places[index].image,
                            name = places[index].name ?: "",
                            location = places[index].category ?: "",
                            onFavoriteClick = {},
                            onClick = {
                                navController.navigate("placeDetails/${places[index].id}")
                            }
                        )
                    }
                }

                is Response.Failure -> {

                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Here's your saved trips!",
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 10.dp, bottom = 6.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        LazyRow(
            modifier = Modifier.height(300.dp),
        ) {
            when (publicTrips) {
                is Response.Loading -> {
                    items(1) {
                        LoadingIndicator()
                    }
                }

                is Response.Success -> {
                    val _publicTrips =
                        (publicTrips as Response.Success<List<TripsItem>>).data
                    Log.d("PlanScreen", "Public Trips: $_publicTrips")

                    items(_publicTrips.size) { index ->
                        TripItem(
                            imageUri = _publicTrips[index].image,
                            name = _publicTrips[index].title ?: "",
                            location = _publicTrips[index].category ?: "",
                            onFavoriteClick = {},
                            onClick = {
                                navController.navigate("tripDetails/${_publicTrips[index].id}")
                            }
                        )
                    }

                }

                else -> {}
            }
        }
    }
}


@Composable
fun TripItem(
    imageUri: String?,
    name: String,
    location: String,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {

    Box(
        modifier = Modifier
            .width(300.dp)
            .wrapContentHeight()
            .padding(10.dp)
            .background(Color.Transparent)
    ) {

        Column(
            modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(20.dp),
                    clip = true,
                    ambientColor = Color(0xFFD5E1FF)
                )
                .clip(RoundedCornerShape(20.dp))
                .height(300.dp)
                .width(280.dp)
                .align(Alignment.Center)
                .background(Color(0xFFD5E1FF))
        ) {
            CoilImage(
                contentDescription = "Trip Image",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(300.dp)
                    .width(280.dp),
                contentScale = ContentScale.Crop,
                data = imageUri,
                title = name,
                desc = location,
                onClick = {
                    onClick()
                },
                onFavoriteClick = {
                    onFavoriteClick()
                }
            )
        }
    }
}