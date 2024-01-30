package com.mobilebreakero.trips.screens.plan


import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.components.AuthButton
import com.mobilebreakero.common_ui.components.MapView
import com.mobilebreakero.common_ui.components.ShowDatePickerDialog
import com.mobilebreakero.common_ui.components.calculateEndDate
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.TRIPS_SCREEN
import com.mobilebreakero.auth_domain.model.Trip
import com.mobilebreakero.auth_domain.util.DataUtils
import com.mobilebreakero.trips.TripsViewModel
import com.mobilebreakero.trips.components.CreateTripButton


enum class TripFilters {
    Study, Work, Vacation, Adventure, Other
}

var tripId = generateRandomTripId()

fun generateRandomTripId(): Int {
    return (100000..999999).random()
}

var selectedLocationpass: String = "Cairo"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTripScreen(
    navController: NavController,
    viewModel: TripsViewModel = hiltViewModel()
) {

    var visitMyBros by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<TripFilters?>(null) }
    var travelReason by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf("Cairo, Egypt") }
    var getGovernment by remember { mutableStateOf(selectedLocationpass) }
    var howLong by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("11/12/2023") }
    val isDateClicked = remember { mutableStateOf(false) }

    var endDate by remember { mutableStateOf("11/12/2023") }
    var tripCategory by remember { mutableStateOf(listOf("")) }
    val isLocationClicked = remember { mutableStateOf(false) }
    val context = LocalContext.current

    val user = DataUtils.user

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Trip Name",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Start)
                .padding(8.dp)
        )
        TextField(
            value = visitMyBros,
            onValueChange = {
                visitMyBros = it
            },
            label = {
                Text(text = "Visit my Bros", color = Color.Black.copy(alpha = 0.3f))
            },
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .width(300.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(.7.dp, Color(0xFF4F80FF), shape = RoundedCornerShape(20.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
        )

        Text(
            text = "Why? ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Start)
                .padding(12.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TripFilters.values().forEach {
                FilterChip(
                    modifier = Modifier
                        .height(50.dp)
                        .width(140.dp)
                        .padding(4.dp),
                    onClick = {
                        selected = it
                        travelReason = it.name
                    },
                    label = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = it.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(2.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    },
                    border = null,
                    selected = it == selected,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF4F80FF),
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFFF8FAFF),
                        disabledContainerColor = Color(0xFFEFEEEE),
                        disabledLabelColor = Color.Black,
                    ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = FilterChipDefaults.filterChipElevation(
                        elevation = 5.dp
                    )
                )
            }
        }

        Text(
            text = "Where? ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Start)
                .padding(12.dp)
        )

        AuthButton(
            onClick = {
                isLocationClicked.value = true
            },
            modifier = Modifier
                .width(300.dp)
                .height(70.dp)
                .padding(12.dp, 12.dp, 20.dp, 12.dp)
                .border(.7.dp, Color(0xFF4F80FF), shape = RoundedCornerShape(20.dp)),
            text = selectedLocation,
            buttonColor = Color.White,
            textColor = Color.Black.copy(alpha = 0.3f)
        )
        Text(
            text = "When? ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Start)
                .padding(12.dp)
        )

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

        Text(
            text = "How Long? ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Start)
                .padding(12.dp)
        )

        TextField(
            value = howLong,
            onValueChange = {
                howLong = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            label = {
                Text(text = "How Long will you stay?", color = Color.Black.copy(alpha = 0.3f))
            },
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier
                .width(300.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(.7.dp, Color(0xFF4F80FF), shape = RoundedCornerShape(20.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
        )

        Spacer(modifier = Modifier.height(15.dp))

        CreateTripButton(text = "Create",
            buttonColor = Color(0xff4F80FF),
            modifier = Modifier
                .align(CenterHorizontally)
                .height(50.dp)
                .width(320.dp),
            onClick = {
                endDate = calculateEndDate(selectedDate, howLong)
                tripCategory = getCategoryBasedOnUserTravelReason(travelReason)
                howLong = howLong.ifBlank {
                    "0"
                }

                viewModel.addTripToFireStore(
                    Trip(
                        id = tripId.toString(),
                        userId = user?.id,
                        why = travelReason,
                        location = selectedLocation,
                        startDate = selectedDate,
                        name = visitMyBros,
                        duration = howLong,
                        endDate = endDate,
                        category = tripCategory,
                        finished = false
                    )
                )
                Toast.makeText(context, "Trip Created Successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(TRIPS_SCREEN)
                selectedLocationpass = getGovernment
            }
        )
    }

    if (isLocationClicked.value) {
        MapView(
            onLocationSelected = {
                selectedLocation = it
                isLocationClicked.value = false
            },
            getGovernment = { governemnt ->
                getGovernment = governemnt
            },
            context = context
        )
    }


    if (isDateClicked.value) {
        ShowDatePickerDialog(selectedDate = selectedDate, onDateSelected = {
            selectedDate = it
            isDateClicked.value = false
        })
    }

}


fun getCategoryBasedOnUserTravelReason(travelReason: String): List<String> {

    return when (travelReason) {
        "Study" -> listOf("Restaurant", "Hotel")
        "Work" -> listOf("Restaurant", "Hotel")
        "Vacation" -> listOf(
            "Mountain",
            "Sea",
            "Restaurant",
            "Hotel",
            "Culture",
            "Beach",
            "Shopping"
        )

        "Adventure" -> listOf("Mountain", "Hotel", "Adventure")
        "Other" -> listOf("Mountain", "Sea", "Restaurant", "Hotel", "Culture")
        else -> listOf("Mountain", "Sea", "Restaurant", "Hotel", "Culture")
    }

}
