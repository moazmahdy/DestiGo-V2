package com.mobilebreakero.details.publicTrips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.components.CoilImage
import com.mobilebreakero.auth_domain.model.TripsItem


@Composable
fun PublicPlacesToVisit(
    trip: TripsItem,
    navController: NavController,
) {

    val places = trip.placesToVisit?.size ?: 0

    LazyRow(
        modifier = Modifier
            .height(300.dp)
            .width(350.dp)
            .clip(RoundedCornerShape(20.dp)),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        items(places) {

            CoilImage(
                contentDescription = "",
                modifier = Modifier
                    .height(270.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.FillBounds,
                data = trip.placesToVisit?.get(it)?.image ?: "",
                onClick = {

                },
                title = trip.placesToVisit?.get(it)?.name ?: "",
                desc = trip.category,
                onFavoriteClick = {}
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}
