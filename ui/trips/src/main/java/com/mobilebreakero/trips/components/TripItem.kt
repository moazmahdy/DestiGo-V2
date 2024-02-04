package com.mobilebreakero.trips.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mobilebreakero.core_ui.components.CoilImage


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
            com.mobilebreakero.core_ui.components.CoilImage(
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