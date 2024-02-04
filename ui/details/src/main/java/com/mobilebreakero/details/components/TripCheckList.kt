package com.mobilebreakero.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.mobilebreakero.core_ui.components.LoadingIndicator
import com.mobilebreakero.details.R
import com.mobilebreakero.auth_domain.model.Trip

@Composable
fun TripCheckList(
    trip: Trip,
    navController: NavController
) {
    val tripJournals = trip.checkList?.size ?: 0

    if (tripJournals == 0) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(400.dp),
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.checklist)
                    .crossfade(true)
                    .build(),
                contentDescription = "Travel",
                modifier = Modifier
                    .fillMaxSize(),
                loading = { com.mobilebreakero.core_ui.components.LoadingIndicator() },
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            ) {
                Text(
                    text = "Whoops! Your trip checklist is empty. 📝😅",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 1.2.em,
                    modifier = Modifier
                        .padding(10.dp)
                )

                Text(
                    text = "Time to get organized! Create your checklist and make sure nothing is left behind. ✅🧳",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(10.dp)
                )

            }
            ElevatedButton(
                onClick = { navController.navigate("planCheckList/${trip.id}") },
                title = "Add New Item",
                icon = R.drawable.checklisticon,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    } else {
        Column {
            LazyColumn(
                modifier = Modifier
                    .height(300.dp)
                    .width(350.dp)
                    .clip(RoundedCornerShape(20.dp)),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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
                                    navController.navigate(
                                        "JournalDetails/${
                                            trip.tripJournal?.get(
                                                it
                                            )?.id
                                        }"
                                    )
                                }
                                .background(Color(0xFFD5E1FF))
                        ) {
                            Text(
                                text = trip.checkList?.get(it)?.name ?: "",
                                fontSize = 20.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(10.dp)
                            )
                            var isChecked by remember { mutableStateOf(false) }

                            val icon = if (isChecked) {
                                Icons.Filled.CheckCircle
                            } else {
                                Icons.Outlined.CheckCircle
                            }

                            Icon(
                                imageVector = icon,
                                contentDescription = "Check Item",
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .padding(4.dp)
                                    .size(30.dp)
                                    .clickable {
                                        isChecked = !isChecked
                                    },
                                tint = Color(0xff4F80FF)
                            )
                        }

                    }
                }
            }

            ElevatedButton(
                onClick = {
                    navController.navigate("planCheckList/${trip.id}")
                },
                title = "add another Item ",
                icon = R.drawable.image,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

    }
}