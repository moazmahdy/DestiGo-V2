package com.mobilebreakero.profile.yourposts


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.auth_domain.model.AppUser
import com.mobilebreakero.auth_domain.model.Post
import com.mobilebreakero.auth_domain.util.Response


@Composable
fun YourPostsScreenContent(
    viewModel: YourPostsViewModel = hiltViewModel(),
    navController: NavController
) {

    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser

    GetUserFromFireStore(
        id = firebaseUser?.uid ?: "",
        user = { userId ->
            userId.id = firebaseUser?.uid
            user.value = userId
        }
    )

    val posts by viewModel.postsFlow.collectAsState()

    val postsResults = viewModel.postsResult

    viewModel.getPosts(userId = user.value.id ?: "")

    if (postsResults.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "You have no posts",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    } else
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
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
                            viewModel.getPosts(user.value.id ?: "")
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