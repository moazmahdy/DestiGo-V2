package com.mobilebreakero.details.components

import android.app.DatePickerDialog
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mobilebreakero.common_ui.components.AuthButton
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.common_ui.components.LinearIndicator
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.auth_data.repoimpl.GenerateRandomIdNumber
import com.mobilebreakero.details.DetailsViewModel
import com.mobilebreakero.details.R
import com.mobilebreakero.auth_domain.model.AppUser
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTripJournal(
    navController: NavController,
    tripId: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser
    val journalName = remember { mutableStateOf("") }
    val journalContent = remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("11/12/2023") }
    val isDateClicked = remember { mutableStateOf(false) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    val uploadProgress by remember { mutableStateOf(0f) }
    var imageLink by remember { mutableStateOf("") }
    var isUploading by remember { mutableStateOf(false) }



    GetUserFromFireStore(
        user = { uId ->
            uId.id = firebaseUser?.uid
            user.value = uId
        },
        id = firebaseUser?.uid ?: "",
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .width(390.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(2.dp, Color(0xFF4F80FF), shape = RoundedCornerShape(20.dp))
                .align(Alignment.Center)
                .background(Color(0xFFF8FAFF)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Add New Journal",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
            OutlinedTextField(
                value = journalName.value,
                onValueChange = {
                    journalName.value = it
                },
                label = { Text(text = "Journal Name") },
                modifier = Modifier
                    .width(300.dp)
                    .height(90.dp)
                    .padding(10.dp),
                maxLines = 1,
                singleLine = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF4F80FF),
                    unfocusedBorderColor = Color(0xFF4F80FF),
                    cursorColor = Color(0xFF4F80FF),
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = journalContent.value,
                onValueChange = {
                    journalContent.value = it
                },
                label = { Text(text = "Just arrived at our destination, mesmerized by breathtaking views. Explored local markets, indulged in delicious street food. Shared laughter and stories around a bonfire under the starry night. Grateful for these unforgettable moments on this amazing trip.") },
                modifier = Modifier
                    .width(300.dp)
                    .height(200.dp)
                    .padding(10.dp),
                maxLines = 5,
                singleLine = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF4F80FF),
                    unfocusedBorderColor = Color(0xFF4F80FF),
                    cursorColor = Color(0xFF4F80FF),
                    focusedLabelColor = Color(0xFF4F80FF).copy(alpha = 0.3f),
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))

            AuthButton(
                onClick = {
                    isDateClicked.value = true
                },
                modifier = Modifier
                    .width(300.dp)
                    .height(70.dp)
                    .padding(12.dp, 12.dp, 20.dp, 12.dp)
                    .border(.7.dp, Color(0xFF4F80FF), shape = RoundedCornerShape(20.dp)),
                text = selectedDate,
                buttonColor = Color.White,
                textColor = Color.Black.copy(alpha = 0.3f)
            )
            Spacer(modifier = Modifier.height(10.dp))
            AddPostButtons(
                onClick = {
                    launcher.launch("image/*")
                },
                iconId = R.drawable.image,
                description = "Photo Icon",
                text = "Upload Photo"
            )
            val context = LocalContext.current

            SubcomposeAsyncImage(
                model = imageUri,
                contentDescription = "Post Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp)),
                contentScale = ContentScale.Crop,
                loading = {
                    LoadingIndicator()
                }
            )

            AddButton(
                onClick = {
                    if (imageUri != null && !isUploading) {
                        isUploading = true
                        uploadImageToStorage(imageUri) { downloadUrl, isSuccessful ->
                            isUploading = false
                            if (isSuccessful as Boolean) {
                                imageLink = downloadUrl
                                imageUri = null
                            }
                        }
                    }
                    viewModel.addTripJournal(
                        tripId = tripId,
                        title = journalName.value,
                        journal = journalContent.value,
                        image = imageLink,
                        date = selectedDate,
                        journalId = GenerateRandomIdNumber().toString()
                    )
                    Toast.makeText(context, "Journal Added", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                buttonColor = Color(0xFF4F80FF),
                text = "Add"
            )
        }
        if (isUploading) {
            LinearIndicator(uploadProgress)
        }

        if (isDateClicked.value) {
            ShowDatePickerDialog(selectedDate = selectedDate, onDateSelected = {
                selectedDate = it
                isDateClicked.value = false
            })
        }

    }
}

@Composable
fun ShowDatePickerDialog(
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formattedDate = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                    .format(Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }.time)
                onDateSelected(formattedDate)
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            if (datePickerDialog.isShowing) {
                datePickerDialog.dismiss()
            }
        }
    }

    LaunchedEffect(selectedDate) {
        if (!datePickerDialog.isShowing) {
            datePickerDialog.updateDate(
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
    }
}

@Composable
fun AddButton(
    onClick: () -> Unit,
    buttonColor: Color? = Color.White,
    text: String,
    border: BorderStroke? = null,
    textColor: Color? = Color.White
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(200.dp)
            .padding(horizontal = 18.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(buttonColor!!),
        border = border
    ) {
        Text(text = text, fontSize = 18.sp, color = textColor!!)
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
fun AddPostButtons(
    onClick: () -> Unit,
    iconId: Int,
    description: String,
    text: String
) {
    Button(
        onClick = onClick,
        border = BorderStroke(0.7.dp, Color(0xff4F80FF)),
        colors = ButtonDefaults.buttonColors(Color.White),
        modifier = Modifier
            .width(300.dp)
            .padding(horizontal = 18.dp, vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = description,
                modifier = Modifier.size(30.dp),
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
