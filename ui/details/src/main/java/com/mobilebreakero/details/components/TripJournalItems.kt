package com.mobilebreakero.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.details.R
import com.mobilebreakero.auth_domain.model.Trip


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripJournal(
    trip: Trip,
    navController: NavController
) {
    val tripJournals = trip.tripJournal?.size ?: 0

    if (tripJournals == 0) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(400.dp),
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://images.unsplash.com/photo-1506485338023-6ce5f36692df?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                    .crossfade(true)
                    .build(),
                contentDescription = "Travel",
                modifier = Modifier
                    .fillMaxSize(),
                loading = { LoadingIndicator() },
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            ) {
                Text(
                    text = "Oops! It looks like your travel journal is empty. 📓✈️",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 1.2.em,
                    modifier = Modifier
                        .padding(10.dp)
                )

                Text(
                    text = "Start documenting your adventures by adding daily entries. 🌍✨",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
            ElevatedButton(
                onClick = { navController.navigate("addTripJournal/${trip.id}") },
                title = "Add New Journal",
                icon = R.drawable.edit,
                modifier = Modifier
                    .padding(10.dp)
                    .align(BottomCenter)
            )
        }
    } else {
        Column {
            val showJournalDetails = remember { mutableStateOf(false) }
            val sheetState = rememberModalBottomSheetState()

            LazyColumn(
                modifier = Modifier
                    .height(300.dp)
                    .width(350.dp)
                    .clip(RoundedCornerShape(20.dp)),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                items(tripJournals) {
                    Box(
                        modifier = Modifier
                            .width(300.dp)
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(20.dp))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    showJournalDetails.value = true
                                }
                                .background(Color(0xFFD5E1FF))
                        ) {
                            trip.tripJournal?.get(it)?.title?.let { it1 ->
                                Text(
                                    text = it1,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                            trip.tripJournal?.get(it)?.date?.let { it1 ->
                                Text(
                                    text = it1,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }
                    }

                    if (showJournalDetails.value) {
                        ModalBottomSheet(
                            onDismissRequest = { showJournalDetails.value = false },
                            sheetState = sheetState,
                            content = {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(10.dp),
                                ) {
                                    SubcomposeAsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(trip.tripJournal?.get(it)?.image)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "Travel",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp),
                                        loading = { LoadingIndicator() },
                                        contentScale = ContentScale.FillBounds
                                    )

                                    trip.tripJournal?.get(it)?.title?.let { it1 ->
                                        Text(
                                            text = it1,
                                            fontSize = 18.sp,
                                            modifier = Modifier.padding(4.dp)
                                        )
                                    }
                                    trip.tripJournal?.get(it)?.date?.let { it1 ->
                                        ItemsChip(title = it1) {
                                        }
                                    }
                                    trip.tripJournal?.get(it)?.content?.let { it1 ->
                                        Text(
                                            text = it1,
                                            fontSize = 14.sp,
                                            modifier = Modifier.padding(4.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(40.dp))
                                }
                            },
                            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                        )
                    }
                }
            }

            ElevatedButton(
                onClick = {
                    navController.navigate("addTripJournal/${trip.id}")
                },
                title = "add another Journal ",
                icon = R.drawable.edit,
                modifier = Modifier.align(CenterHorizontally)
            )
        }

    }
}