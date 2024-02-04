package com.mobilebreakero.details

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mobilebreakero.core_ui.components.LoadingIndicator
import com.mobilebreakero.core_ui.components.ShowDatePickerDialog
import com.mobilebreakero.core_ui.components.calculateEndDate
import com.mobilebreakero.auth_data.repoimpl.GenerateRandomIdNumber
import com.mobilebreakero.details.components.ItemsChip
import com.mobilebreakero.details.components.PlacesToVisit
import com.mobilebreakero.details.components.TripCheckList
import com.mobilebreakero.details.components.TripDetailsCard
import com.mobilebreakero.details.components.TripImages
import com.mobilebreakero.details.components.TripJournal
import com.mobilebreakero.auth_domain.model.Trip
import com.mobilebreakero.auth_domain.util.DataUtils
import com.mobilebreakero.auth_domain.util.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun TripDetailsScreen(
    tripId: String,
    viewModel: DetailsViewModel = hiltViewModel(),
    navController: NavController
) {

    LaunchedEffect(tripId){
        viewModel.getTripDetailsResult(tripId)
    }

    val tripDetails by viewModel.tripDetailsResult.collectAsState()

    when (tripDetails) {
        is Response.Success -> {
            val results = (tripDetails as Response.Success<Trip>).data
            TripDetails(results, navController)
        }

        is Response.Failure -> {

        }

        else -> {
            Response.Loading
            com.mobilebreakero.core_ui.components.LoadingIndicator()
        }
    }

}

@Composable
fun TripDetails(
    trip: Trip,
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    var isUpdatingTrip by remember { mutableStateOf(false) }
    var tripNameUpdate by remember { mutableStateOf("") }
    var tripDaysUpdate by remember { mutableStateOf("") }

    TripDetailsCard(title = trip.name ?: "Trip details", onEditClick = {
        isUpdatingTrip = true
    }, content = {

        var imageUri by remember { mutableStateOf<Uri?>(null) }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            imageUri = uri
        }

        var currentProgress by remember { mutableFloatStateOf(0f) }
        var newPhotoUrl by remember { mutableStateOf(trip.image) }
        var imageLink by remember { mutableStateOf("") }
        var isUploading by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        var selectedDate by remember { mutableStateOf("") }
        val isDateClicked = remember { mutableStateOf(false) }
        var tripDays by remember { mutableStateOf(trip.duration ?: "") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .align(Alignment.TopCenter)
                ) {

                    SubcomposeAsyncImage(
                        model = Uri.parse(newPhotoUrl),
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                launcher.launch("image/*")
                            },
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        loading = { com.mobilebreakero.core_ui.components.LoadingIndicator() })
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.2f))
                    )
                }
            }

            if (imageUri != null && !isUploading) {
                isUploading = true
                uploadImageToStorage(imageUri) { downloadUrl, isSuccessful ->
                    if (isSuccessful as Boolean) {
                        newPhotoUrl = downloadUrl
                        imageLink = newPhotoUrl.toString()
                        viewModel.addPhoto(photo = imageLink, id = trip.id ?: "")
                        isUploading = false
                        imageUri = null
                    }
                }
            }

            LaunchedEffect(isUploading) {
                scope.launch {
                    loadProgress { progress ->
                        currentProgress = progress
                    }
                }
            }

            if (isUploading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    color = Color.Blue,
                    progress = currentProgress
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Column(
                modifier = Modifier
                    .wrapContentSize()
            ) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .horizontalScroll(rememberScrollState())
                ) {

                    val fromDate =
                        if (selectedDate != "") {
                            selectedDate
                        } else {
                            trip.startDate
                        }

                    ItemsChip(title = "From $fromDate") {
                        isDateClicked.value = true
                    }

                    var endDate by remember { mutableStateOf(trip.endDate) }

                    if (selectedDate.isNotBlank()) {
                        endDate = com.mobilebreakero.core_ui.components.calculateEndDate(
                            selectedDate,
                            tripDays
                        )
                    } else if (tripDaysUpdate.isNotBlank()) {
                        val startDate: String = trip.startDate ?: ""
                        endDate = com.mobilebreakero.core_ui.components.calculateEndDate(
                            startDate,
                            tripDaysUpdate
                        )
                    }

                    if (tripDaysUpdate.isNotBlank()) {
                        tripDays = tripDaysUpdate
                        endDate = com.mobilebreakero.core_ui.components.calculateEndDate(
                            startDate = trip.startDate ?: "", tripDaysUpdate
                        )
                    }

                    ItemsChip(title = "To $endDate") {
                    }

                }

                Text(
                    text = "Places to visit",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Here's a list of places you saved to visit during your trip.",
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.padding(start = 10.dp, bottom = 6.dp)
                )
                PlacesToVisit(trip = trip, navController = navController)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Your Trip Journal", fontSize = 20.sp, color = Color.Black)
                Text(
                    text = "Here you can document your trip and save your best memories.",
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.padding(start = 10.dp, bottom = 6.dp)
                )
                TripJournal(trip = trip, navController = navController)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Your Trip Images", fontSize = 20.sp, color = Color.Black)
                Text(
                    text = "Here you can save your best trip images",
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.padding(start = 10.dp, bottom = 6.dp)
                )
                TripImages(trip = trip, navController = navController)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Your Trip CheckList", fontSize = 20.sp, color = Color.Black)
                Text(
                    text = "Here's your stuff you need to pack for your trip",
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.padding(start = 10.dp, bottom = 6.dp)
                )
                TripCheckList(trip = trip, navController = navController)
            }
        }

        if (isUpdatingTrip) {
            AlertDialog(
                onDismissRequest = {
                    isUpdatingTrip = false
                },
                title = { Text("Update Trip") },
                text = {
                    Column(
                        verticalArrangement = spacedBy(10.dp),
                    ) {
                        OutlinedTextField(
                            value = tripNameUpdate,
                            onValueChange = { tripNameUpdate = it },
                            label = { Text("New trip name") }
                        )
                        OutlinedTextField(
                            value = tripDaysUpdate,
                            onValueChange = { tripDaysUpdate = it },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            label = { Text("New trip stay days ") }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F80FF)),
                        onClick = {
                            if (tripNameUpdate.isNotEmpty()) {
                                viewModel.updateTripName(tripNameUpdate, trip.id ?: "")
                            }
                            if (tripDaysUpdate.isNotEmpty()) {

                                viewModel.updateTripDays(tripDaysUpdate, trip.id ?: "")
                            }
                            isUpdatingTrip = false
                        }
                    ) {
                        Text("Update")
                    }
                }
            )
        }

        if (isDateClicked.value) {
            com.mobilebreakero.core_ui.components.ShowDatePickerDialog(
                selectedDate = selectedDate,
                onDateSelected = {
                    selectedDate = it
                    isDateClicked.value = false
                    viewModel.updateTripDate(
                        id = trip.id ?: "",
                        date = selectedDate
                    )
                })
        }
    })
}


fun uploadImageToStorage(uri: Uri?, onComplete: (String, Any?) -> Unit) {
    val store = Firebase.storage
    val storageRef = store.reference
    val imageRef = storageRef.child("tripCover/${DataUtils.user?.id}${GenerateRandomIdNumber()}}")

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
