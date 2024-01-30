package com.mobilebreakero.profile.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.auth_domain.model.AppUser
import com.mobilebreakero.auth_domain.model.Trip
import com.mobilebreakero.auth_domain.model.TripsItem
import com.mobilebreakero.auth_domain.util.Response
import com.mobilebreakero.profile.yourtrips.YourTripsViewModel

@Composable
fun YourTripsScreen(
    viewModel: YourTripsViewModel = hiltViewModel(),
    navController: NavController
) {

    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser

    GetUserFromFireStore(
        id = firebaseUser?.uid ?: "",
        user = { userId ->
            userId.id = firebaseUser?.uid
            user.value = userId
        }
    )

    val yourtrips by viewModel.tripsFlow.collectAsState()
    val publicTrips by viewModel.publicTripsFlow.collectAsState()

    viewModel.getTrips(user.value.id ?: "")
    viewModel.getPublicTrips(user.value.id ?: "")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp, top = 60.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (yourtrips) {
            is Response.Loading -> {
                items(1) {
                    LoadingIndicator()
                }
            }

            is Response.Success -> {
                val trips_ = (yourtrips as Response.Success<List<Trip>>).data

                if (trips_.isNotEmpty()) {
                    items(trips_.size) { index ->
                        trips_[index].name?.let {
                            trips_[index].location?.let { it1 ->
                                TripItem(
                                    imageUri = trips_[index].image,
                                    name = it,
                                    location = it1,
                                    onFavoriteClick = {
                                    },
                                    onClick = {
                                        navController.navigate(
                                            "tripDetails/${trips_[index].id}"
                                        )
                                    }
                                )
                            }
                        }
                    }
                } else {
                    items(1) {
                    }
                }
            }

            else -> {}
        }

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

                if (_publicTrips.isNotEmpty()) {
                    items(_publicTrips.size) { index ->
                        TripItem(
                            imageUri = _publicTrips[index].image,
                            name = _publicTrips[index].title ?: "",
                            location = _publicTrips[index].category ?: "",
                            onFavoriteClick = {},
                            onClick = {
                                navController.navigate("publicTripDetails/${_publicTrips[index].tripId}")
                            }
                        )
                    }
                } else {
                    items(1) {
                    }
                }
            }

            else -> {
            }
        }
    }
}