package com.mobilebreakero.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.mobilebreakero.common_ui.components.CoilImage
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.details.DetailsViewModel
import com.mobilebreakero.details.R
import com.mobilebreakero.auth_domain.model.Trip
import com.mobilebreakero.auth_domain.util.Response


@Composable
fun PlacesToVisit(
    trip: Trip,
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    val places = trip.places?.size ?: 0

    if (places == 0) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(400.dp),
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://images.unsplash.com/photo-1679682753106-2cb1b5125a94?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
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
                    text = "Uh-oh! Your trip is looking a bit empty. 🌍😮",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 1.2.em,
                    modifier = Modifier
                        .padding(10.dp)
                )

                Text(
                    text = "Discover and save exciting places to make your trip unforgettable! 🗺️✨",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
            ElevatedButton(
                onClick = { navController.navigate("AddPlaces/${trip.id}") },
                title = "Explore Places",
                icon = R.drawable.search,
                modifier = Modifier
                    .padding(10.dp)
                    .align(BottomCenter)
            )
        }
    } else
        LazyRow(
            modifier = Modifier
                .height(300.dp)
                .width(350.dp)
                .clip(RoundedCornerShape(20.dp)),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            items(places) {
                val photos = viewModel.photoResult
                viewModel.getPhoto(trip.places?.get(it)?.location ?: "")
                CoilImage(
                    contentDescription = "",
                    modifier = Modifier
                        .height(270.dp)
                        .width(200.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.FillBounds,
                    data = if (photos is Response.Success && photos.data.isNotEmpty()) {
                        val imageUrl = photos.data[it]?.images?.large?.url
                        imageUrl
                    } else {
                        "https://firebasestorage.googleapis.com/v0/b/destigo-84de1.appspot.com/o/placephotoholder.png?alt=media&token=46cd051e-06a8-4a58-ac1c-7073f05175db"
                    },
                    onClick = {
                        navController.navigate("savePlacesDetails/${trip.places?.get(it)?.location}/${trip.id}")
                    },
                    title = trip.places?.get(it)?.name ?: "",
                    desc = trip.places?.get(it)?.date ?: "click to update",
                    onFavoriteClick = {
                    }
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
}
