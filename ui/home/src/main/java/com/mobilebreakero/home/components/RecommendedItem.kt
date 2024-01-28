package com.mobilebreakero.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mobilebreakero.common_ui.components.CoilImage


@Composable
fun ForYouItem(
    title: String,
    desc: String,
    image: String? = "https://egypttimetravel.com/wp-content/uploads/2020/06/Cairo.jpg",
    onSaveCLick: () -> Unit,
    onItemClick: () -> Unit,
    isSaved: Boolean = false,
    icon : Int? = null
) {


    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(start = 10.dp)
    ) {

        CoilImage(
            contentDescription = "",
            modifier = Modifier
                .height(275.dp)
                .width(210.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.FillBounds,
            data = image,
            onClick = { onItemClick() },
            title = title,
            desc = desc,
            onFavoriteClick = { onSaveCLick() },
            saved = isSaved,
            icon = icon
        )

    }
}