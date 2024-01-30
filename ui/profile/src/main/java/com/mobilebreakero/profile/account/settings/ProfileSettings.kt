package com.mobilebreakero.profile.account.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.auth_domain.model.AppUser
import com.mobilebreakero.auth_domain.util.DataUtils.user
import com.mobilebreakero.profile.R
import com.mobilebreakero.profile.component.ProfileImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProfileSettingsContent(viewModel: ProfileSettingsViewModel = hiltViewModel()) {

    val user = remember { mutableStateOf(AppUser()) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    var uploadProgress by remember { mutableStateOf(0f) }

    val userStatus by remember { mutableStateOf("Iam a software engineer") }
    val newLocation by remember { mutableStateOf("Cairo, Egypt") }

    var newStatus by remember { mutableStateOf(user.value.status ?: userStatus) }
    var userLocation by remember { mutableStateOf(user.value.location ?: newLocation) }


    var newPhotoUrl by remember { mutableStateOf("") }
    var imageLink by remember { mutableStateOf(user.value.photoUrl ?: newPhotoUrl) }
    var isUploading by remember { mutableStateOf(false) }

    var isChangingStatus by remember { mutableStateOf(false) }
    var isChangingLocation by remember { mutableStateOf(false) }

    val firebaseUser = Firebase.auth.currentUser

    val scope = rememberCoroutineScope()

    GetUserFromFireStore(
        user = { uId ->
            uId.id = firebaseUser?.uid
            user.value = uId
        },
        id = firebaseUser?.uid ?: "",
    )

    LaunchedEffect(user.value.photoUrl, user.value.status, user.value.location) {
        newPhotoUrl = user.value.photoUrl ?: ""
        newStatus = user.value.status ?: userStatus
        userLocation = user.value.location ?: newLocation
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .height(500.dp)
                .width(390.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(2.dp, Color(0xFF4F80FF), shape = RoundedCornerShape(20.dp))
                .align(Alignment.Center)
                .background(Color(0xFFF8FAFF)),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            (if (user.value.id != null) user.value.name else "")?.let {
                Text(
                    text = it,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4F80FF)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = userLocation,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(CenterHorizontally),
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = newStatus,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(10.dp)
                    .align(CenterHorizontally),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard(
                icon = R.drawable.photo,
                text = "change photo",
                onClick = { launcher.launch("image/*") })
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard(icon = R.drawable.info, text = "update your status", onClick = {
                isChangingStatus = true
            })
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard(icon = R.drawable.location, text = "change your location", onClick = {
                isChangingLocation = true
            })
            Spacer(modifier = Modifier.height(10.dp))
        }

        if (imageUri != null) {
            isUploading = true
            uploadImageToStorage(imageUri) { downloadUrl, isSuccessful ->
                if (isSuccessful as Boolean) {
                    imageLink = downloadUrl
                    newPhotoUrl = imageLink
                    viewModel.updatePhoto(user.value.id!!, imageLink)
                    imageUri = null
                    isUploading = false
                }
            }
        }

        if (isUploading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                progress = uploadProgress
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(bottom = 5.dp, top = 60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            ProfileImage(
                data = Uri.parse(newPhotoUrl),
                contentDescription = "",
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .align(Alignment.CenterVertically)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }

    if (isChangingStatus) {
        AlertDialog(
            onDismissRequest = {
                isChangingStatus = false
            },
            title = { Text("Update Status") },
            text = {
                newStatus.let {
                    OutlinedTextField(
                        value = it,
                        onValueChange = { newStatus = it },
                        label = { Text("New Status") }
                    )
                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F80FF)),
                    onClick = {
                        if (user.value.id != null) {
                            newStatus.let {
                                viewModel.updateStatus(
                                    user.value.id.toString(),
                                    status = it
                                )
                            }
                        }
                        isChangingStatus = false
                    }
                ) {
                    Text("Update")
                }
            }
        )
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

    if (isChangingLocation) {
        AlertDialog(
            onDismissRequest = {
                isChangingLocation = false
            },
            title = { Text("Update Location") },
            text = {
                userLocation.let {
                    OutlinedTextField(
                        value = it,
                        onValueChange = { userLocation = it },
                        label = { Text("Cairo, Egypt") }
                    )
                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F80FF)),
                    onClick = {
                        if (user.value.id != null) {
                            userLocation.let {
                                viewModel.updateLocation(
                                    user.value.id!!,
                                    location = it
                                )
                            }
                        }
                        isChangingLocation = false
                    }
                ) {
                    Text("Update")
                }
            }
        )
    }
}

fun uploadImageToStorage(uri: Uri?, onComplete: (String, Any?) -> Unit) {
    val store = Firebase.storage
    val storageRef = store.reference
    val imageRef = storageRef.child("profilePhoto/${user?.id}")

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

suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(100)
    }
}