package com.mobilebreakero.common_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest


@Composable
fun CoilImage(
    data: Any? = null,
    contentDescription: String,
    modifier: Modifier,
    contentScale: ContentScale,
    onClick: () -> Unit,
    title: String? = null,
    desc: String? = null,
    onFavoriteClick: () -> Unit,
    saved: Boolean = false,
    icon: Int? = null
) {

    var isSaved by remember { mutableStateOf(saved) }

    Box(
        modifier = modifier
            .clickable { onClick() }
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale,
            loading = {
                LoadingIndicator()
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.4f))
                .height(83.dp)
                .align(Alignment.BottomStart),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                if (title != null) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .padding(5.dp)
                            .align(CenterHorizontally),
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
                if (desc != null) {
                    Text(
                        text = desc,
                        modifier = Modifier
                            .align(CenterHorizontally),
                        fontSize = 8.sp,
                        color = Color.White
                    )
                }
            }

        }

        icon?.let {
            Icon(
                painter = painterResource(icon),
                tint = Color.White,
                contentDescription = "Favorite Icon",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(30.dp)
                    .padding(end = 10.dp, top = 10.dp)
                    .clickable {
                        isSaved = !isSaved
                        onFavoriteClick()
                    }
            )
        }
    }
}