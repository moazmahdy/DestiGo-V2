package com.mobilebreakero.home

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.core_ui.components.GetUserFromFireStore
import com.mobilebreakero.core_ui.components.LoadingIndicator
import com.mobilebreakero.core_ui.extensions.rememberZoomState
import com.mobilebreakero.core_ui.extensions.zoom
import com.mobilebreakero.auth_domain.model.AppUser
import com.mobilebreakero.auth_domain.model.Post
import com.mobilebreakero.auth_domain.util.Response
import com.mobilebreakero.home.components.ProfileImage
import com.mobilebreakero.viewModel.HomeViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailsScreen(
    postId: String,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {

    val details by viewModel.detailsResult.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPostDetails(postId)
    }

    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser
    LaunchedEffect(postId) {
        viewModel.getReviews()
    }
    val reviewsResponse = viewModel.getReviews

    val numberOfReviews = 15
    val randomReviews = reviewsResponse.shuffled().take(numberOfReviews)

    com.mobilebreakero.core_ui.components.GetUserFromFireStore(
        id = firebaseUser?.uid ?: "",
        user = { userId ->
            userId.id = firebaseUser?.uid
            user.value = userId
        }
    )

    when (details) {
        is Response.Success -> {
            val postDetails = (details as Response.Success<Post>).data
            val profilePhoto by remember { mutableStateOf(postDetails.profilePhoto) }
            val name by remember { mutableStateOf(postDetails.userName) }
            val location by remember { mutableStateOf(postDetails.location) }
            val imageUri by remember { mutableStateOf(postDetails.image) }
            val postStatus by remember { mutableStateOf(postDetails.text) }
            val context = LocalContext.current
            val likeCount = remember { mutableIntStateOf(postDetails.numberOfLikes) }
            val isLikeBy =
                remember { mutableStateOf(postDetails.likedUserIds.contains(user.value.id)) }

            var isExpanded by remember { mutableStateOf(false) }


            val sheetState = rememberModalBottomSheetState()
            val isShown = remember { mutableStateOf(false) }

            val scrollState = rememberScrollState()

            Box(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .height(1000.dp)
                        .background(Color(0xFFF8FAFF))
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        ProfileImage(
                            contentDescription = "Profile Photo",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                            data = Uri.parse(profilePhoto)
                        )
                        Column(
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            name?.let {
                                Text(
                                    text = it,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4F80FF)
                                )
                            }
                            Row {
                                Icon(
                                    painter = painterResource(id = R.drawable.location),
                                    contentDescription = "Location Icon",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color(0xFF4F80FF)
                                )
                                location?.let {
                                    Text(
                                        text = it,
                                        fontSize = 9.sp,
                                    )
                                }
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.End
                        ) {
                            DropdownMenu(
                                expanded = isExpanded,
                                onDismissRequest = { isExpanded = false }
                            ) {
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.deletePost(postId = postId)
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

                    Spacer(modifier = Modifier.height(8.dp))

                    postStatus?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4F80FF)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    SubcomposeAsyncImage(
                        model = Uri.parse(imageUri),
                        contentDescription = "Post Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .padding(3.dp)
                            .clip(
                                RoundedCornerShape(
                                    bottomEnd = 12.dp,
                                    bottomStart = 12.dp,
                                    topStart = 5.dp,
                                    topEnd = 5.dp
                                )
                            )
                            .clickable {
                                isShown.value = true
                            },
                        loading = {
                            com.mobilebreakero.core_ui.components.LoadingIndicator()
                        },
                        contentScale = ContentScale.FillBounds
                    )
                    LaunchedEffect(Unit) {
                        likeCount.intValue = postDetails.numberOfLikes
                    }
                    if (isShown.value) {
                        ModalBottomSheet(
                            onDismissRequest = { isShown.value = false },
                            sheetState = sheetState,
                            content = {
                                postDetails.image?.let { ViewImage(image = it) }
                            },
                            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(start = 20.dp, bottom = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PostContent(
                                icon = if (isLikeBy.value) R.drawable.likefilled else R.drawable.like,
                                description = "Like Icon",
                                text = "${likeCount.intValue}",
                            ) {
                                isLikeBy.value = !isLikeBy.value
                                if (isLikeBy.value) {
                                    likeCount.intValue++
                                } else {
                                    if (likeCount.intValue > 0)
                                        likeCount.intValue--
                                }
                                viewModel.likePost(
                                    postId = postId,
                                    userId = user.value.id!!,
                                    context = context,
                                    likes = likeCount.intValue,
                                )
                            }
                            Spacer(modifier = Modifier.width(90.dp))
                            PostContent(
                                icon = R.drawable.comment,
                                description = "Comment Icon",
                                text = "comment"
                            ) {
                                navController.navigate("comment/${postDetails.id}")
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                            PostContent(
                                icon = R.drawable.share,
                                description = "Share Icon",
                                text = "share"
                            ) {
                                viewModel.sharePost(
                                    postId = postDetails.id!!,
                                    userId = postDetails.userId!!,
                                    userName = postDetails.userName!!,
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
                            }
                        }
                    }

                    if (postDetails.comments != null) {
                        Box(
                            modifier = Modifier
                                .height(.5.dp)
                                .fillMaxWidth()
                                .background(Color(0xFF4F80FF))
                        )
                        Text(
                            text = "Comments",
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4F80FF)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        val fakeReviewsSize = randomReviews.size
                        val commentsSize = postDetails.comments?.size ?: 0

                        val size =
                            if (commentsSize > 0) fakeReviewsSize + commentsSize else fakeReviewsSize


                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp)
                                .align(Alignment.CenterHorizontally),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (commentsSize > 0) {
                                items(commentsSize) { commentIndex ->
                                    CommentItem(
                                        commenter = postDetails.comments!![commentIndex].userName!!,
                                        comment = postDetails.comments!![commentIndex].text!!
                                    )
                                }
                            }

                            items(size) { index ->
                                val reviewIndex = index - commentsSize

                                randomReviews.getOrNull(reviewIndex)?.let { review ->
                                    CommentItem(
                                        commenter = review.user!!,
                                        comment = review.review!!
                                    )
                                }
                            }
                        }
                    }

                }
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "settings",
                    modifier = Modifier
                        .clickable {
                            isExpanded = user.value.id == postDetails.id
                        }
                        .align(TopEnd)
                        .padding(start = 130.dp),
                    tint = Color(0xFF4F80FF)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        is Response.Failure -> {
            Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
        }

        else -> {
            com.mobilebreakero.core_ui.components.LoadingIndicator()
        }
    }


}

@Composable
fun CommentItem(commenter: String, comment: String) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color(0xFFD5E1FF).copy(alpha = 0.5f))
            .border(
                width = .2.dp,
                color = Color(0xFF4F80FF),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Text(
            text = commenter,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            fontSize = 18.sp,
            color = Color(0xFF4F80FF)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = comment, modifier = Modifier.padding(8.dp), fontSize = 14.sp)
        Spacer(modifier = Modifier.height(5.dp))
    }
}


@Composable
fun PostContent(icon: Int, description: String, text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0xFF4F80FF),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = description,
                modifier = Modifier
                    .size(20.dp),
                tint = Color(0xFF4F80FF)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = text, color = Color.Gray, fontSize = 8.sp)
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
            verticalAlignment = CenterVertically,
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
fun ViewImage(
    image: String,
) {
    val context = LocalContext.current
    var isImageSaved by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ElevatedButton(title = "Save", onClick = {
                saveImageIntoDevice(context, image).let {
                    isImageSaved = true
                }
            }, icon = R.drawable.save)
            Spacer(modifier = Modifier.width(10.dp))
            ElevatedButton(
                title = "Share",
                onClick = { shareImage(context, image) },
                icon = R.drawable.share
            )
        }

        if (isImageSaved) {
            Toast.makeText(context, "Image saved", Toast.LENGTH_SHORT).show()
        }

        val zoomState = com.mobilebreakero.core_ui.extensions.rememberZoomState()
        Spacer(modifier = Modifier.height(10.dp))
        SubcomposeAsyncImage(
            model = image,
            contentDescription = null,
            loading = {
                com.mobilebreakero.core_ui.components.LoadingIndicator()
            },
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .zoom(
                    zoomState = zoomState,
                    onDoubleTap = { position ->
                        val targetScale = when {
                            zoomState.scale < 2f -> 2f
                            zoomState.scale < 4f -> 4f
                            else -> 1f
                        }
                        zoomState.changeScale(targetScale, position)
                    }
                )
        )
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun saveImageIntoDevice(context: Context, image: String) {
    GlobalScope.launch(Dispatchers.Main) {
        val bitmap = loadBitmapFromUrl(context, image)
        bitmap?.let {
            val uri = saveBitmapToCache(context, bitmap)
            val openIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                setDataAndType(uri, "image/jpeg")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(openIntent)
        }
    }
}

fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "image_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }

    val contentResolver = context.contentResolver
    val imageUri = contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    )

    try {
        imageUri?.let {
            contentResolver.openOutputStream(it)?.use { outputStream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                    return null
                }
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }

    return imageUri
}


@OptIn(DelicateCoroutinesApi::class)
fun shareImage(context: Context, image: String) {
    GlobalScope.launch(Dispatchers.Main) {
        val bitmap = loadBitmapFromUrl(context, image)
        bitmap?.let {
            val uri = saveBitmapToCache(context, bitmap)
            uri?.let {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_STREAM, uri)
                    type = "image/jpeg"
                }
                val shareIntent = Intent.createChooser(sendIntent, "Share Image")
                context.startActivity(shareIntent)
            }
        }
    }
}

suspend fun loadBitmapFromUrl(context: Context, imageUrl: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .target { drawable ->
                    if (drawable is BitmapDrawable) {
                        return@target
                    }
                }
                .build()

            val imageLoader = ImageLoader(context)
            val result = imageLoader.execute(request)

            if (result is SuccessResult) {
                result.drawable.toBitmap()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}