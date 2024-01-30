package com.mobilebreakero.trips.screens.plan

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.common_ui.navigation.NavigationRoutes
import com.mobilebreakero.auth_domain.model.AppUser
import com.mobilebreakero.auth_domain.model.Trip
import com.mobilebreakero.auth_domain.model.TripsItem
import com.mobilebreakero.auth_domain.util.Response
import com.mobilebreakero.trips.TripsViewModel
import com.mobilebreakero.trips.components.CreateTripButton
import com.mobilebreakero.trips.components.TripItem


@Composable
fun PlanScreen(
    navController: NavController,
    viewModel: TripsViewModel = hiltViewModel()
) {

    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser

    GetUserFromFireStore(id = firebaseUser?.uid ?: "",
        user = { userId ->
            userId.id = firebaseUser?.uid
            user.value = userId
        })

    val trips by viewModel.tripsFlow.collectAsState()
    val tripsResult = viewModel.tripsResult

    val publicTrips by viewModel.publicTripsFlow.collectAsState()
    val publicTrips_ = viewModel.publicTripResult

    viewModel.getTrips(userId = user.value.id ?: "")
    viewModel.getPublicTrips(userId = user.value.id ?: "")

    if (tripsResult.isEmpty() && publicTrips_.isEmpty()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SubcomposeAsyncImage(
                model = "https://m.media-amazon.com/images/I/51lPLv7rdOL._SY466_.jpg",
                contentDescription = "Travel",
                loading = { LoadingIndicator() },
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(0.4f))
            )
            Column(
                modifier = Modifier
                    .align(BottomStart)
                    .padding(10.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "Embark on your next adventure with DestiGo!",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 1.2.em
                )
                Text(
                    text = "Unlock your journey's potential with DestiGo! Plan your perfect trip, from weekend getaways to dream vacations.",
                    fontSize = 16.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .width(170.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xff4F80FF))
                        .clickable {
                            navController.navigate(NavigationRoutes.CREATE_TRIP)
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Let's plan First Trip", fontSize = 13.sp, color = Color.White)
                }
            }
        }

    } else {
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.LightGray.copy(alpha = 0.3f),
                modifier = Modifier
                    .wrapContentWidth()
                    .height(50.dp)
                    .align(TopCenter)
                    .border(
                        width = .4.dp,
                        color = Color(0xff4F80FF),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clip(shape = RoundedCornerShape(20.dp)),
            ) {
                Tab(
                    text = { Text("Visited") },
                    selected = selectedTabIndex == 0,
                    onClick = {
                        selectedTabIndex = 0
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .width(70.dp),
                    selectedContentColor = Color(0xff4F80FF),
                    unselectedContentColor = Color(0xff4F80FF).copy(alpha = 0.4f)
                )

                Tab(
                    text = { Text("Ongoing") },
                    selected = selectedTabIndex == 1,
                    onClick = {
                        selectedTabIndex = 1
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .width(70.dp),
                    selectedContentColor = Color(0xff4F80FF),
                    unselectedContentColor = Color(0xff4F80FF).copy(alpha = 0.4f)
                )

                Tab(
                    text = { Text("SavedTrips") },
                    selected = selectedTabIndex == 2,
                    onClick = {
                        selectedTabIndex = 2
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .width(70.dp),
                    selectedContentColor = Color(0xff4F80FF),
                    unselectedContentColor = Color(0xff4F80FF).copy(alpha = 0.4f)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp, top = 60.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                when (trips) {
                    is Response.Loading -> {
                        items(1) {
                            LoadingIndicator()
                        }
                    }

                    is Response.Success -> {
                        when (selectedTabIndex) {
                            0 -> {
                                val trips_ = (trips as Response.Success<List<Trip>>).data
                                val finishedTrips =
                                    trips_.filter {
                                        it.finished == true
                                    }
                                if (finishedTrips.isNotEmpty()) {
                                    items(finishedTrips.size) { index ->
                                        finishedTrips[index].name?.let {
                                            finishedTrips[index].location?.let { it1 ->
                                                TripItem(
                                                    imageUri = finishedTrips[index].image,
                                                    name = it,
                                                    location = it1,
                                                    onFavoriteClick = {
                                                    },
                                                    onClick = {
                                                        navController.navigate(
                                                            "tripDetails/${finishedTrips[index].id}"
                                                        )
                                                    }
                                                )
                                            }
                                        }
                                    }
                                } else {
                                    items(1) {
                                        Text(text = "No trips Finished yet", fontSize = 24.sp)
                                    }
                                }
                            }

                            1 -> {
                                val trips_ = (trips as Response.Success<List<Trip>>).data
                                val ongoingTrips =
                                    trips_.filter { it.finished != true }

                                if (ongoingTrips.isNotEmpty()) {
                                    items(ongoingTrips.size) { index ->
                                        ongoingTrips[index].name?.let {
                                            ongoingTrips[index].location?.let { it1 ->
                                                TripItem(
                                                    imageUri = ongoingTrips[index].image,
                                                    name = it,
                                                    location = it1,
                                                    onFavoriteClick = {
                                                    },
                                                    onClick = {
                                                        navController.navigate(
                                                            "tripDetails/${ongoingTrips[index].id}"
                                                        )
                                                    }
                                                )
                                            }
                                        }
                                    }
                                } else {
                                    items(1) {
                                        Text(
                                            text = "No ongoing trips, \nyou can start by creating a new trip",
                                            fontSize = 24.sp
                                        )
                                    }
                                }
                            }

                            2 -> {
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
                                                    navController.navigate("publicTripDetails/${_publicTrips[index].tripId}")
                                                }
                                            )
                                        }

                                    }

                                    else -> {
                                        items(1) {
                                            Text(text = "No Saved trips", fontSize = 24.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    else -> {
                    }

                }
            }
            CreateTripButton(text = "add another Trip Plan ",
                buttonColor = Color(0xff4F80FF),
                modifier = Modifier
                    .height(50.dp)
                    .align(BottomCenter)
                    .width(300.dp),
                onClick = { navController.navigate(NavigationRoutes.CREATE_TRIP) }
            )
        }

    }
}