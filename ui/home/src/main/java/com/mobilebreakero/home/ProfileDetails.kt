package com.mobilebreakero.home


import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.auth_domain.model.AppUser
import com.mobilebreakero.auth_domain.model.Post
import com.mobilebreakero.auth_domain.repo.postResponse
import com.mobilebreakero.auth_domain.util.Response
import com.mobilebreakero.home.components.PostItem
import com.mobilebreakero.home.components.ProfileImage
import com.mobilebreakero.viewModel.HomeViewModel

@Composable
fun ProfileDetailsScreen(
    userID: String?,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val user = remember { mutableStateOf(AppUser()) }

    val posts by viewModel.postsIdFlow.collectAsState()

    val postsResults = viewModel.postsResult

    viewModel.getPostsById(userId = user.value.id ?: "")

    GetUserFromFireStore(
        id = userID,
        user = { userId ->
            userId.id = userID
            user.value = userId
        }
    )


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            UserDetails(user = user)
            Spacer(modifier = Modifier.height(12.dp))
            PostsLabel(
                user = user,
                viewModel = viewModel,
                posts = posts,
                postsResults = postsResults,
                navController = navController
            )
        }
    }
}


@Composable
fun UserDetails(user: MutableState<AppUser>) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        ProfileImage(
            data = Uri.parse(user.value.photoUrl),
            contentDescription = "profile photo",
            modifier = Modifier
                .size(80.dp)
                .clip(
                    CircleShape
                ),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = user.value.name!!,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4F80FF)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = user.value.status!!,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 10.dp, bottom = 20.dp, start = 15.dp, end = 15.dp)
        )
    }
}


@Composable
fun PostsLabel(
    user: MutableState<AppUser>,
    viewModel: HomeViewModel,
    posts: postResponse,
    postsResults: List<Post>,
    navController: NavController
) {
    Column {
        Text(
            text = "   ${user.value.name!!} posts",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        if (postsResults.isEmpty()) {
            Spacer(modifier = Modifier.height(80.dp))
            Text(text = "Loading.....")
        } else {
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

                        val posts = (posts).data

                        items(posts.size) { index ->
                            val context = LocalContext.current

                            var postsLikes by remember { mutableIntStateOf(posts[index].numberOfLikes) }

                            var isLiked by
                            remember { mutableStateOf(posts[index].likedUserIds.contains(user.value.id)) }

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
                                onProfileClick = {},
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
                                isLiked = isLiked,
                                onSettingsClick = {

                                }
                            )
                        }
                    }

                    else -> {}
                }

            }
        }
    }
}