package com.mobilebreakero.details.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.details.DetailsViewModel
import com.mobilebreakero.details.R
import com.mobilebreakero.details.ViewImage
import com.mobilebreakero.common_ui.extensions.rememberZoomState
import com.mobilebreakero.common_ui.extensions.zoom
import com.mobilebreakero.details.loadProgress
import com.mobilebreakero.details.uploadImageToStorage
import com.mobilebreakero.domain.model.Trip
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripImages(
    trip: Trip,
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    val images = trip.tripImages?.size ?: 0

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    var uploadProgress by remember { mutableStateOf(0f) }
    var newPhotoUrl by remember { mutableStateOf("") }
    var imageLink = ""
    var isUploading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(newPhotoUrl) {
        imageLink = newPhotoUrl
    }

    if (images == 0) {

        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(400.dp),
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://images.unsplash.com/photo-1454430690613-c5fbdb397f65?q=80&w=1950&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                    .crossfade(true)
                    .build(),
                contentDescription = "Travel",
                modifier = Modifier
                    .fillMaxSize(),
                loading = { LoadingIndicator() },
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            ) {

                Text(
                    text = "Your trip gallery is empty. 📸😕",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 1.2.em,
                    modifier = Modifier
                        .padding(10.dp)
                )

                Text(
                    text = "Capture the moments and create your best trip gallery! 🌟🗺️",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
            ElevatedButton(
                onClick = {
                    launcher.launch("image/*")
                },
                title = "Add new image",
                icon = R.drawable.image,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.BottomCenter)
            )


            if (imageUri != null && !isUploading) {
                isUploading = true
                uploadImageToStorage(imageUri) { downloadUrl, isSuccessful ->
                    if (isSuccessful as Boolean) {
                        newPhotoUrl = downloadUrl
                        imageLink = newPhotoUrl
                        viewModel.addPlacePhoto(photo = imageLink, tripId = trip.id ?: "")
                        imageUri = null
                        isUploading = false
                    }
                }
            }

            LaunchedEffect(isUploading) {
                scope.launch {
                    loadProgress { progress ->
                        uploadProgress = progress
                    }
                }
            }

            if (isUploading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    color = Color.Blue,
                    progress = uploadProgress
                )
            }

        }
    } else {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {

            items(images) { index ->
                val image = trip.tripImages
                val sheetState = rememberModalBottomSheetState()
                val isShown = remember { mutableStateOf(false) }

                if (image != null) {
                    if (image.isNotEmpty())
                        SubcomposeAsyncImage(
                            model = image[index].images,
                            modifier = Modifier
                                .height(180.dp)
                                .width(150.dp)
                                .clickable {
                                    isShown.value = true
                                }
                                .padding(5.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            loading = { LoadingIndicator() })
                }
                if (isShown.value) {
                    ModalBottomSheet(
                        onDismissRequest = { isShown.value = false },
                        sheetState = sheetState,
                        content = {
                            image?.get(index)?.images?.let { ViewImage(image = it) }
                        },
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    )
                }

            }
        }

        ElevatedButton(onClick = {
            launcher.launch("image/*")
        }, title = "upload another photo ", icon = R.drawable.image)

        if (imageUri != null && !isUploading) {
            isUploading = true
            uploadImageToStorage(imageUri) { downloadUrl, isSuccessful ->
                if (isSuccessful as Boolean) {
                    newPhotoUrl = downloadUrl
                    imageLink = newPhotoUrl
                    viewModel.addPlacePhoto(photo = imageLink, tripId = trip.id ?: "")
                    imageUri = null
                    isUploading = false
                }
            }
        }

        LaunchedEffect(isUploading) {
            scope.launch {
                loadProgress { progress ->
                    uploadProgress = progress
                }
            }
        }

        if (isUploading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                color = Color.Blue,
                progress = uploadProgress
            )
        }
    }
}
