package com.mobilebreakero.home

import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.common_ui.components.GuideCardDesign
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.common_ui.design_system.Modifiers
import com.mobilebreakero.common_ui.design_system.Padding
import com.mobilebreakero.common_ui.design_system.SpacerHeights
import com.mobilebreakero.common_ui.components.VSpacer
import com.mobilebreakero.common_ui.design_system.Borders
import com.mobilebreakero.common_ui.design_system.fontSize
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.CREATE_TRIP
import com.mobilebreakero.auth_data.repoimpl.GenerateRandomIdNumber
import com.mobilebreakero.auth_domain.model.AppUser
import com.mobilebreakero.auth_domain.model.Post
import com.mobilebreakero.auth_domain.model.TripsItem
import com.mobilebreakero.auth_domain.util.Response
import com.mobilebreakero.home.components.AddButtonDesign
import com.mobilebreakero.home.components.ForYouItem
import com.mobilebreakero.home.components.PostItem
import com.mobilebreakero.home.components.TitleText
import com.mobilebreakero.home.components.TopScreenImage
import com.mobilebreakero.viewModel.HomeViewModel
import kotlinx.coroutines.launch

private const val selectedIndex = -1
private val travelImage = R.drawable.travel
private val destinationsImage = R.drawable.destinations
private val socialImage = R.drawable.blogging

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {

    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser
    val textModifier = Modifier.padding(start = Padding.medium, bottom = Padding.small)

    GetUserFromFireStore(
        id = firebaseUser?.uid ?: "",
        user = { userId ->
            userId.id = firebaseUser?.uid
            user.value = userId
        }
    )

    val posts by viewModel.postsFlow.collectAsState()

    LaunchedEffect(posts) {
        viewModel.getPosts()
    }

    val userRecommended = viewModel.userRecommendations
    val userRecommendedPlaces = viewModel.userRecommendationsPlaces

    LaunchedEffect(user.value) {
        viewModel.getRecommendations(user.value.interestedPlaces ?: listOf())
        viewModel.getRecommendedPlaces(user.value.interestedPlaces ?: listOf())
    }

    viewModel.getPublicTrips(user.value.id ?: "")
    val publicTripsResult = viewModel.publicTripResult

    var showPlacesBottomSheet by remember { mutableStateOf(false) }
    var showTripsBottomSheet by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(
        modifier = Modifiers
            .fillMaxSize
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifiers.fillMaxSize,
        ) {
            (if (user.value.id != null) user.value.name else "user")?.let {
                TopScreenImage(
                    user = it,
                    navController = navController
                )
            }

            var selectedPlacesItemIndex by remember { mutableIntStateOf(selectedIndex) }
            var selectedTripsItemIndex by remember { mutableIntStateOf(selectedIndex) }

            VSpacer(height = SpacerHeights.large)
            GuideCardDesign(
                title = stringResource(id = R.string.getReady),
                description = stringResource(id = R.string.exploreDestinations),
                image = travelImage,
                buttonLabel = stringResource(id = R.string.letsCreateLabel),
                onClick = {
                    navController.navigate(CREATE_TRIP)
                }
            )

            VSpacer(height = SpacerHeights.large)

            TitleText(text = stringResource(R.string.forYou))

            Text(
                text = stringResource(id = R.string.recommendTrips),
                fontSize = fontSize.small,
                modifier = textModifier
            )
            VSpacer(height = SpacerHeights.small)
            LazyRow {
                items(userRecommended.size) { index ->
                    var isTripSaved by remember { mutableStateOf(false) }

                    if (publicTripsResult.isNotEmpty()) {
                        for (trip in publicTripsResult) {
                            isTripSaved =
                                publicTripsResult.any { it.userId == userRecommended[index]?.userId }
                        }
                    }
                    userRecommended[index]?.title?.let {
                        userRecommended[index]?.reasonForTravel?.let { it1 ->
                            ForYouItem(
                                title = it,
                                desc = it1,
                                image = userRecommended[index]?.image,
                                onItemClick = {
                                    selectedTripsItemIndex = index
                                    showTripsBottomSheet = true
                                },
                                isSaved = isTripSaved,
                                icon = if (isTripSaved) {
                                    R.drawable.favorite_filled
                                } else {
                                    R.drawable.favorite_border
                                },
                                onSaveCLick = {
                                    val tripItem = TripsItem(
                                        id = userRecommended[index]?.id,
                                        userId = user.value.id,
                                        tripId = GenerateRandomIdNumber().toString(),
                                        title = userRecommended[index]?.title,
                                        image = userRecommended[index]?.image,
                                        isSaved = isTripSaved,
                                        fullJourney = userRecommended[index]?.fullJourney,
                                        placesToVisit = userRecommended[index]?.placesToVisit,
                                        description = userRecommended[index]?.description,
                                        reasonForTravel = userRecommended[index]?.reasonForTravel,
                                    )
                                    if (isTripSaved) {
                                        Toast.makeText(
                                            context,
                                            "Trip already saved!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        isTripSaved = !isTripSaved
                                    } else {
                                        isTripSaved = !isTripSaved
                                        viewModel.savePublicTrips(
                                            trip = tripItem
                                        )
                                        viewModel.updateSaves(
                                            user.value.id ?: "",
                                            null,
                                            tripItem
                                        )
                                        Toast.makeText(
                                            context,
                                            "Trip saved! You can find it in your trips",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            )
                        }
                    }
                }
            }
            val sheetState = rememberModalBottomSheetState()
            VSpacer(height = SpacerHeights.xlarge)
            GuideCardDesign(
                image = destinationsImage,
                title = stringResource(id = R.string.getPlaces),
                description = stringResource(id = R.string.youAreNotLost),
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(
                            scrollState.value + 800,
                            spring(
                                Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessVeryLow
                            )
                        )
                    }
                },
                buttonLabel = stringResource(id = R.string.letsExploreLabel)
            )
            VSpacer(height = SpacerHeights.large)
            TitleText(text = stringResource(id = R.string.bestDestinationsForYou))
            VSpacer(height = SpacerHeights.small)
            LazyRow {
                items(userRecommendedPlaces.size) { index ->
                    val item = userRecommendedPlaces[index]
                    var isPlaceSaved by remember {
                        mutableStateOf(
                            userRecommendedPlaces[index]?.isSaved ?: false
                        )
                    }
                    userRecommendedPlaces[index]?.name?.let {
                        userRecommendedPlaces[index]?.category?.let { it1 ->
                            ForYouItem(
                                title = it,
                                desc = it1,
                                image = userRecommendedPlaces[index]?.image,
                                onItemClick = {
                                    selectedPlacesItemIndex = index
                                    showPlacesBottomSheet = true
                                },
                                icon = if (isPlaceSaved) {
                                    R.drawable.favorite_filled
                                } else {
                                    R.drawable.favorite_border
                                },
                                onSaveCLick = {
                                    viewModel.updateSaves(
                                        user.value.id ?: "",
                                        item,
                                        null
                                    )
                                    Toast.makeText(
                                        context,
                                        "Trip saved! You can find it in your trips",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    isPlaceSaved = !isPlaceSaved
                                }
                            )
                        }
                    }
                }
            }

            if (showPlacesBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showPlacesBottomSheet = false
                    },
                    tonalElevation = 1.dp,
                    sheetState = sheetState,
                ) {
                    Column {
                        SubcomposeAsyncImage(
                            model = userRecommendedPlaces[selectedPlacesItemIndex]?.image,
                            contentDescription = "place image",
                            modifier = Modifier
                                .padding(5.dp)
                                .height(200.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.FillBounds,
                            loading = {
                                LoadingIndicator()
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = userRecommendedPlaces[selectedPlacesItemIndex]?.name ?: "",
                            modifier = Modifier.padding(10.dp),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(10.dp)
                                .border(.5.dp, Color(0xff4F80FF), RoundedCornerShape(20.dp))
                        ) {
                            Text(
                                text = "${userRecommendedPlaces[selectedPlacesItemIndex]?.city ?: ""}, ",
                                modifier = Modifier.padding(4.dp),
                                fontSize = 15.sp,
                            )
                            Text(
                                text = userRecommendedPlaces[selectedPlacesItemIndex]?.country
                                    ?: "",
                                modifier = Modifier.padding(4.dp),
                                fontSize = 15.sp,
                            )
                        }
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
                                    text = userRecommendedPlaces[selectedPlacesItemIndex]?.category
                                        ?: "",
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = userRecommendedPlaces[selectedPlacesItemIndex]?.description
                                        ?: "",
                                    modifier = Modifier.padding(5.dp),
                                    fontSize = 15.sp,
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                        Text(
                            text = "The activity you can do here is",
                            modifier = Modifier.padding(5.dp),
                            fontSize = 15.sp,
                        )
                        Text(
                            text = userRecommendedPlaces[selectedPlacesItemIndex]?.activity
                                ?: "",
                            modifier = Modifier.padding(
                                start = 10.dp,
                                top = 3.dp,
                                bottom = 3.dp
                            ),
                            fontSize = 18.sp,
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                    }
                }
            }

            if (showTripsBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showTripsBottomSheet = false
                    },
                    tonalElevation = 1.dp,
                    sheetState = sheetState,
                ) {
                    Column() {
                        SubcomposeAsyncImage(
                            model = userRecommended[selectedTripsItemIndex]?.image,
                            contentDescription = "place image",
                            modifier = Modifier
                                .padding(5.dp)
                                .height(130.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.FillBounds,
                            loading = {
                                LoadingIndicator()
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = userRecommended[selectedTripsItemIndex]?.title ?: "",
                            modifier = Modifier.padding(10.dp),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(10.dp)
                                .border(.5.dp, Color(0xff4F80FF), RoundedCornerShape(20.dp))
                        ) {
                            Text(
                                text = "from ${userRecommended[selectedTripsItemIndex]?.fullJourney?.startDate ?: ""}, ",
                                modifier = Modifier.padding(4.dp),
                                fontSize = 15.sp,
                            )
                            Text(
                                text = "to ${userRecommended[selectedTripsItemIndex]?.fullJourney?.endDate ?: ""}, ",
                                modifier = Modifier.padding(4.dp),
                                fontSize = 15.sp,
                            )
                        }
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
                                    text = userRecommended[selectedTripsItemIndex]?.category
                                        ?: "",
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = userRecommended[selectedTripsItemIndex]?.description
                                        ?: "",
                                    modifier = Modifier.padding(5.dp),
                                    fontSize = 15.sp,
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                        Text(
                            text = "The places you can visit here is",
                            modifier = Modifier.padding(5.dp),
                            fontSize = 15.sp,
                        )
                        LazyRow(
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(10.dp)
                        ) {
                            items(
                                userRecommended[selectedTripsItemIndex]?.placesToVisit?.size
                                    ?: 0
                            ) {
                                ForYouItem(
                                    title = userRecommended[selectedTripsItemIndex]?.placesToVisit?.get(
                                        it
                                    )?.name ?: "",
                                    desc = "",
                                    onSaveCLick = { /*TODO*/ },
                                    image = userRecommended[selectedTripsItemIndex]?.placesToVisit?.get(
                                        it
                                    )?.image ?: "",
                                    onItemClick = {}
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "The full Journey ",
                            modifier = Modifier.padding(5.dp),
                            fontSize = 15.sp,
                        )
                        Column {
                            Row {
                                ItemsChip(title = "Start Date ${userRecommended[selectedTripsItemIndex]?.fullJourney?.startDate ?: ""}") {}
                                ItemsChip(title = "End Date ${userRecommended[selectedTripsItemIndex]?.fullJourney?.endDate ?: ""}") {}
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            VSpacer(height = SpacerHeights.xlarge)

            GuideCardDesign(
                image = socialImage,
                title = stringResource(id = R.string.youCanPost),
                description = stringResource(id = R.string.takeAlook),
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(
                            scrollState.value + 800,
                            spring(
                                Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessVeryLow
                            )
                        )
                    }
                },
                buttonLabel = stringResource(id = R.string.letsTakeAlook)
            )

            VSpacer(height = SpacerHeights.large)
            TitleText(text = stringResource(R.string.travellersPosts))

            Text(
                text = stringResource(id = R.string.youCanPost),
                fontSize = fontSize.small,
                modifier = textModifier
            )
            VSpacer(height = SpacerHeights.small)
            LazyColumn(
                modifier = Modifier
                    .height(450.dp)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (posts) {
                    is Response.Loading -> {
                    }

                    is Response.Success -> {

                        val posts = (posts as Response.Success<List<Post>>).data

                        items(posts.size) { index ->

                            val context = LocalContext.current

                            var postsLikes by remember { mutableIntStateOf(posts[index].numberOfLikes) }

                            var isLiked by remember {
                                mutableStateOf(
                                    posts[index].likedUserIds.contains(
                                        user.value.id
                                    )
                                )
                            }

                            val userProfile = posts[index].userId
                            var isExpanded by remember { mutableStateOf(false) }

                            LaunchedEffect(posts) {
                                viewModel.getPosts()
                            }

                            LaunchedEffect(Unit) {
                                postsLikes = posts[index].numberOfLikes
                            }

                            PostItem(
                                name = posts[index].userName!!,
                                numberOfLike = postsLikes,
                                location = posts[index].location!!,
                                imageUri = posts[index].image!!,
                                profilePhoto = posts[index].profilePhoto!!,
                                text = posts[index].text ?: "",
                                onLikeClick = {
                                    isLiked = !isLiked
                                    if (isLiked) {
                                        postsLikes += 1
                                    } else if (postsLikes > 0) {
                                        postsLikes -= 1
                                    }
                                    viewModel.likePost(
                                        postId = posts[index].id!!,
                                        userId = user.value.id!!,
                                        context = context,
                                        likes = postsLikes,
                                    )
                                },
                                onCommentClick = {
                                    navController.navigate("comment/${posts[index].id}")
                                },
                                onPostClick = {
                                    navController.navigate("postDetails/${posts[index].id}")
                                },
                                onProfileClick = {
                                    navController.navigate("profileDetails/${userProfile}")
                                },
                                onShareClick = {

                                    viewModel.sharePost(
                                        postId = posts[index].id!!,
                                        userId = user.value.id!!,
                                        userName = user.value.name!!,
                                    )

                                    when (viewModel.sharePostResponse) {

                                        is Response.Success -> {
                                            Toast.makeText(
                                                context,
                                                "Post shared successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                        is Response.Failure -> {
                                            Toast.makeText(
                                                context,
                                                "Error sharing post",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                        else -> {
                                        }
                                    }
                                },
                                onSettingsClick = {
                                    if (user.value.id == posts[index].userId) {
                                        isExpanded = true
                                    }
                                },
                                isLiked = isLiked
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                DropdownMenu(
                                    expanded = isExpanded,
                                    onDismissRequest = { isExpanded = false }
                                ) {
                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.deletePost(postId = posts[index].id!!)
                                            isExpanded = false
                                        },
                                        text = { Text(text = "Delete") },
                                        modifier = Modifier
                                            .background(Color.White)
                                            .fillMaxWidth()
                                    )
                                }

                            }

                        }
                    }

                    else -> {

                    }
                }

            }
        }
    }
    AddButtonDesign(navController = navController)
}

@Composable
fun ItemsChip(title: String, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(Color.Transparent)
            .clip(RoundedCornerShape(20.dp))
            .wrapContentHeight()
            .wrapContentWidth()
            .clickable { onClick() }
            .border(Borders.mainBorder, RoundedCornerShape(20.dp))
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            modifier = Modifier.padding(10.dp),
        )
    }

}