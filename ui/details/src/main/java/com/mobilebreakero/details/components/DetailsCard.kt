package com.mobilebreakero.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DetailsCard(
    title: String,
    details: String,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
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
                    ambientColor = Color(0xFFEDF1FD)
                )
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(Color(0xFFEDF1FD))
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = details,
                fontSize = 14.sp,
                modifier = Modifier.padding(2.dp),
            )

            content()
        }

    }
}
@Composable
fun AmenitiesCard(
    title: String,
    details: List<String?>,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .width(350.dp)
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
                    ambientColor = Color(0xFFEDF1FD)
                )
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(Color(0xFFEDF1FD))
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold
            )

            Column(
                modifier = Modifier.padding(2.dp)
            ) {
                details.forEach {
                    val amenityLines = it?.split(" - ")
                    if (amenityLines?.size == 1) {
                        Text(
                            text = "- \"$it\"",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(2.dp),
                            textAlign = TextAlign.Start
                        )
                    } else {
                        Row {
                            amenityLines?.forEachIndexed { index, amenity ->
                                Text(
                                    text = "- \"$amenity\"",
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .weight(1f),
                                    textAlign = if (index == 0) TextAlign.Start else TextAlign.End
                                )
                            }
                        }
                    }
                }
            }

            content()
        }
    }
}
