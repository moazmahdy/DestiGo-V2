package com.mobilebreakero.welcome.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mobilebreakero.navigation_core.NavigationRoutes.START_SCREEN
import com.mobilebreakero.welcome.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomePager(
    navController: NavController
) {

    val coroutineScope = rememberCoroutineScope()
    val images: List<Int> = listOf(
        R.drawable.adventure,
        R.drawable.wandercompass,
        R.drawable.memoirmakers,
        R.drawable.journeyquest
    )
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        images.size
    }
    val titles = listOf(
        "Adventure",
        "WanderCompass",
        "MemoirMakers",
        "JourneyQuest"
    )


    val descriptions = listOf(
        "Discover hidden gems and breathtaking landscapes with DestiGo. Plan your dream trips, create personalized itineraries, and explore vibrant destinations worldwide. Let your wanderlust guide you to new horizons and create lifelong memories.",
        "Welcome to DestiGo, your personalized travel companion. Get tailored recommendations based on your preferences and interests. Discover the best attractions, restaurants, and accommodations wherever you go. Let us inspire your next adventure.",
        "With DestiGo, document your travel experiences in a digital journal. Upload photos, write entries, and relive your favorite moments. Share your adventures with a vibrant community of fellow travelers. Inspire and be inspired by their stories.",
        "Open the door to unforgettable experiences with DestiGo. Explore detailed destination information, find hidden treasures, and immerse yourself in local cultures. Plan effortlessly with our intuitive trip planner and access trusted reviews from fellow travelers. Let DestiGo be your guide to extraordinary journeys."
    )

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) {


        Box(modifier = Modifier.fillMaxWidth()) {

            WelcomePage(
                image = images[it],
                title = titles[it],
                description = descriptions[it],
                pagerState = pagerState,
                textNext = if (pagerState.currentPage < 3) {
                    "Next"
                } else {
                    "Get Started"
                },
                onClick = {
                    if (pagerState.currentPage < 3) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        navController.navigate(START_SCREEN)
                    }
                },
                skipClick = {
                    navController.navigate(START_SCREEN)
                }
            )
        }
    }
}
