package com.mobilebreakero.welcome.components

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomePage(
    image: Int,
    title: String,
    description: String,
    textNext: String,
    onClick: () -> Unit,
    pagerState: PagerState,
    skipClick: () -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {

        SubcomposeAsyncImage(
            model = image,
            contentDescription = "Welcome Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            loading = {
                CircularProgressIndicator()
            }
        )

        Column(
            modifier = Modifier
                .align(
                    Alignment.Center
                )
                .padding(10.dp)
        ) {

            Spacer(modifier = Modifier.height(150.dp))

            Text(
                text = title,
                fontSize = 32.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .height(50.dp)
            )

            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                Modifier
                    .height(24.dp)
                    .width(100.dp)
                    .align(CenterHorizontally),
                horizontalArrangement = Arrangement.Start
            ) {
                repeat(4) { iteration ->
                    val lineWeight = animateFloatAsState(
                        targetValue = if (pagerState.currentPage == iteration) {
                            1.0f
                        } else {
                            if (iteration < pagerState.currentPage) {
                                0.5f
                            } else {
                                0.5f
                            }
                        }, label = "size", animationSpec = tween(300, easing = EaseInOut)
                    )
                    val color =
                        if (pagerState.currentPage == iteration) Color(0xFF4F80FF) else Color.White
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(color)
                            .weight(lineWeight.value)
                            .height(10.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .align(BottomCenter)
                .fillMaxWidth()
        ) {
            NextButton(
                textNext,
                onClick,
                modifier = Modifier
                    .padding(start = 16.dp, end = 30.dp, bottom = 20.dp)
            )
            Text(
                text = "Skip",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 10.dp, end = 16.dp, start = 30.dp)
                    .clickable { skipClick() },
            )
        }

    }
}