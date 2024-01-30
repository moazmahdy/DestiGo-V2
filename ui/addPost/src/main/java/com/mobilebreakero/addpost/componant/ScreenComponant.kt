package com.mobilebreakero.addpost.componant

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mobilebreakero.addpost.R
import com.mobilebreakero.addpost.viewmodel.AddPostViewModel
import com.mobilebreakero.common_ui.components.MapView
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.auth_domain.model.Post
import com.mobilebreakero.auth_domain.util.DataUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun AddPostCard(navController: NavController, viewModel: AddPostViewModel = hiltViewModel()) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    var uploadProgress by remember { mutableStateOf(0f) }
    val user = DataUtils.user
    var imageLink by remember { mutableStateOf("") }
    var isUploading by remember { mutableStateOf(false) }
    var selectedLocation by remember { mutableStateOf("Cairo, Egypt") }
    var isLocationClicked by remember { mutableStateOf(false) }
    var isAddPostSatus by remember { mutableStateOf(false) }
    var newStatus by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = Color(0xFF4F80FF),
                shape = RoundedCornerShape(18.dp),
            )
            .width(350.dp)
            .wrapContentHeight()
            .background(Color(0xFFF8FAFF))
            .clip(RoundedCornerShape(18.dp))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {

            TitleText()
            if (isLocationClicked) {
                MapView(onLocationSelected = {
                    selectedLocation = it
                    isLocationClicked = false
                }, getGovernment = {
                }, context = context)
            }
            AddPostButtons(iconId = R.drawable.text, text = "add your status", onClick = {
                isAddPostSatus = true
            }, description = "Text Icon")

            AddPostButtons(
                onClick = {
                    isLocationClicked = true
                },
                iconId = R.drawable.location,
                description = "Location Icon",
                text = selectedLocation
            )
            AddPostButtons(
                onClick = {
                    launcher.launch("image/*")
                },
                iconId = R.drawable.photo,
                description = "Photo Icon",
                text = "Upload Photo"
            )
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(5.dp),
                model = imageUri,
                contentDescription = "Add Post Image",
            )

            PostOrCancelSection(navController = navController, onClick = {
                if (imageUri != null && !isUploading) {
                    isUploading = true
                    uploadImageToStorage(imageUri) { downloadUrl, isSuccessful ->
                        if (isSuccessful as Boolean) {
                            imageLink = downloadUrl
                            val post = Post(
                                id = GenerateRandomIdNumber().toString(),
                                userId = user?.id,
                                userName = user?.name,
                                image = imageLink,
                                location = selectedLocation,
                                profilePhoto = user?.photoUrl,
                                text = newStatus,
                            )
                            viewModel.addPost(post)
                            imageUri = null
                            isUploading = false
                            navController.navigate(HOME_SCREEN)
                        }
                    }
                }
            })
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

            if (isAddPostSatus) {
                AlertDialog(
                    onDismissRequest = {
                        isAddPostSatus = false
                    },
                    title = { Text("Update Status") },
                    text = {
                        OutlinedTextField(
                            value = newStatus,
                            onValueChange = { newStatus = it },
                            label = { Text("New Status") }
                        )
                    },
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F80FF)),
                            onClick = {
                                isAddPostSatus = false
                            }
                        ) {
                            Text("add post")
                        }
                    }
                )
            }


        }
    }
}

fun GenerateRandomIdNumber(): Int {
    return (100000000..999999999).random()
}

@Composable
fun TitleText() {
    Text(
        text = "Add Post",
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        modifier = Modifier.padding(12.dp)
    )
}

@Composable
fun AddPostButtons(
    onClick: () -> Unit,
    iconId: Int,
    description: String,
    text: String
) {
    Button(
        onClick = onClick,
        border = BorderStroke(0.5.dp, Color(0xff4F80FF)),
        colors = ButtonDefaults.buttonColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = description,
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF4F80FF)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = text,
                color = Color.LightGray,
                fontSize = 18.sp
            )
        }
    }
}

fun uploadImageToStorage(uri: Uri?, onComplete: (String, Any?) -> Unit) {
    val store = Firebase.storage
    val storageRef = store.reference
    val imageRef = storageRef.child("images/${GenerateRandomIdNumber()}")

    if (uri == null) {
        onComplete("", false)
        return
    }

    val uploadTask = imageRef.putFile(uri)

    uploadTask.continueWithTask { task ->
        if (!task.isSuccessful) {
            task.exception?.let {
                throw it
            }
        }
        imageRef.downloadUrl
    }.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val downloadUri = task.result.toString()
            onComplete(downloadUri, true)
        } else {
            onComplete("", false)
        }
    }
}


@Composable
fun PostOrCancelSection(
    navController: NavController,
    onClick: () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(40.dp))
        ScreenButton(
            onClick = {
                onClick()
            },
            buttonColor = Color(0xFF4F80FF),
            text = "Post"
        )
        Spacer(modifier = Modifier.width(20.dp))
        ScreenButton(
            onClick = { navController.navigate(HOME_SCREEN) },
            border = BorderStroke(1.dp, Color(0xff4F80FF)),
            text = "Cancel",
            textColor = Color.Black
        )
    }
}

@Composable
fun ScreenButton(
    onClick: () -> Unit,
    buttonColor: Color? = Color.White,
    text: String,
    border: BorderStroke? = null,
    textColor: Color? = Color.White
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .wrapContentWidth()
            .padding(horizontal = 18.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(buttonColor!!),
        border = border
    ) {
        Text(text = text, fontSize = 18.sp, color = textColor!!)
    }
}

suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(100)
    }
}