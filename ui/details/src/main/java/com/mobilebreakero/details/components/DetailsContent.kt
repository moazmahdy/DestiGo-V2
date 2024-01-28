package com.mobilebreakero.details.components

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import coil.compose.SubcomposeAsyncImage
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.domain.model.DetailsResponse
import com.mobilebreakero.domain.model.PhotoDataItem
import com.mobilebreakero.domain.model.ReviewItem


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsContent(
    photos: List<PhotoDataItem?>,
    detailsResponse: DetailsResponse,
    reviewResponse: List<ReviewItem?>?
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        photos.size
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
            ) { page ->
                val photo = photos[page]?.images?.large?.url ?: ""
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = photo,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        loading = { LoadingIndicator() })
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.2f))
                    )
                }
            }

            Row(
                Modifier
                    .height(24.dp)
                    .padding(start = 4.dp)
                    .width(100.dp)
                    .align(Alignment.BottomStart),
                horizontalArrangement = Arrangement.Start
            ) {
                repeat(photos.size) { iteration ->
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

        Text(
            text = detailsResponse.name ?: "",
            fontSize = 22.sp,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = detailsResponse.rankingData?.rankingString ?: "",
            fontSize = 16.sp,
            modifier = Modifier.padding(3.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        DetailsCard(
            title = "Location Details",
            details = detailsResponse.addressObj?.addressString ?: ""
        ) {
            Spacer(modifier = Modifier.height(20.dp))
        }

        DetailsCard(title = "About", details = detailsResponse.description ?: "") {
            Spacer(modifier = Modifier.height(20.dp))
        }

        if (detailsResponse.amenities?.isNotEmpty() == true)
            AmenitiesCard(title = "Amenities", details = detailsResponse.amenities ?: listOf("")) {
                Spacer(modifier = Modifier.height(20.dp))
            } else
            Spacer(modifier = Modifier.height(20.dp))

        val numberOfReviews = 15
        val randomReviews = reviewResponse?.shuffled()?.take(numberOfReviews)

        Text(text = "Reviews", fontSize = 18.sp, modifier = Modifier.padding(8.dp))

        LazyColumn(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(randomReviews?.size ?: 0) { index ->
                randomReviews?.get(index)?.user?.let {
                    randomReviews[index]?.review?.let { it1 ->
                        randomReviews[index]?.dateofReview?.let { it2 ->
                            ReviewItemCard(
                                it,
                                it1,
                                it2
                            )
                        }
                    }
                }
            }
        }

    }
}


@Composable
fun ElevatedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    icon: Int
) {
    Box(
        modifier = modifier
            .shadow(1.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF4F80FF))
            .height(40.dp)
            .width(160.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.White
            )

            Text(
                text = title,
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun ReviewItemCard(commenter: String, comment: String, date: String) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 5.dp, vertical = 10.dp)
            .background(Color(0xFFD5E1FF).copy(alpha = 0.3f))
            .border(
                width = .2.dp,
                color = Color(0xFF4F80FF),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Text(
            text = commenter,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            fontSize = 24.sp,
            color = Color(0xFF4F80FF)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = date,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            fontSize = 10.sp,
            color = Color(0xFF4F80FF)
        )
        Text(text = comment, modifier = Modifier.padding(8.dp), fontSize = 12.sp)
        Spacer(modifier = Modifier.height(5.dp))
    }
}
