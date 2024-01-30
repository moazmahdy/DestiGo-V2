package com.mobilebreakero.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.auth_domain.model.DataItem
import com.mobilebreakero.auth_domain.model.PhotoDataItem


@Composable
fun SearchResultItem(
    item: DataItem,
    navController: NavController,
    photoDataItem: PhotoDataItem?) {

    Box(
        modifier = Modifier
            .width(350.dp)
            .height(200.dp)
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
                .height(150.dp)
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .clickable {
                    navController.navigate("details/${item.locationId}")
                }
                .background(Color(0xFFD5E1FF))
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.width(135.dp))
                Column {
                    item.name?.let {
                        Text(
                            text = it,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 15.dp, start = 15.dp),
                            fontSize = 14.sp
                        )
                    }
                    Row {
                        item.addressObj?.country?.let {
                            Text(
                                text = "$it, ",
                                color = Color.Black,
                                modifier = Modifier.padding(top = 15.dp, start = 15.dp),
                                fontSize = 8.sp
                            )
                        }
                        item.addressObj?.city?.let {
                            Text(
                                text = it,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 15.dp),
                                fontSize = 8.sp
                            )
                        }
                    }
                }
            }
        }

        if (photoDataItem != null) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photoDataItem.images?.large?.url)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .height(170.dp)
                    .width(135.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentDescription = null,
                contentScale = FillBounds,
                loading = {
                    LoadingIndicator()
                },
            )
        }
    }
}