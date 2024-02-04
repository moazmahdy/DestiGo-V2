package com.mobilebreakero.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobilebreakero.navigation_core.NavigationRoutes.SEARCH_SCREEN
import com.mobilebreakero.home.R

@Composable
fun TopScreenImage(user: String, navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Image(
            painter = painterResource(id = R.drawable.memories),
            contentDescription = "Home top Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp))
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clip(RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp))
        )
        Text(
            text = "Hello $user",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 85.dp)
                .shadow(10.dp)
        )
        val image = R.drawable.ic_search

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter).fillMaxWidth()
                .padding(top = 200.dp)
        ) {
            com.mobilebreakero.core_ui.components.DestiGoButton(
                onClick = {
                    navController.navigate(SEARCH_SCREEN)
                },
                modifier = Modifier
                    .wrapContentWidth()
                    .height(83.dp)
                    .padding(12.dp, 12.dp, 20.dp, 12.dp)
                    .shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(22.dp),
                        clip = true
                    ),
                text = "Where do you want to go?",
                buttonColor = Color(0xFFEFEEEE),
                border = BorderStroke(0.5.dp, Color.Black.copy(alpha = 0.1f)),
                textColor = Color.Black.copy(alpha = 0.3f),
                icon = image
            )
        }
    }
}