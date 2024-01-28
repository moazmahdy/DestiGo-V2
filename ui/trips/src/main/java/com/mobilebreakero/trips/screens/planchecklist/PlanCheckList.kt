package com.mobilebreakero.trips.screens.planchecklist

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.navigation.NavigationRoutes
import com.mobilebreakero.data.repoimpl.GenerateRandomIdNumber
import com.mobilebreakero.trips.TripsViewModel
import com.mobilebreakero.trips.components.CreateTripButton

@Composable
fun PlanCheckListScreen(
    navController: NavController,
    viewModel: TripsViewModel = hiltViewModel(),
    tripId: String,
    screenRoot: String
) {
    var chickListItems by remember { mutableStateOf(listOf("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Check List",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(8.dp)
        )

        chickListItems.forEachIndexed { index, item ->
            CheckLitItem(text = item,
                onTextChanged =
                { newText ->
                    chickListItems =
                        chickListItems.toMutableList().apply { this[index] = newText }
                })
        }

        Spacer(modifier = Modifier.height(80.dp))

        CreateTripButton(text = "Add",
            buttonColor = Color(0xff4F80FF),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(50.dp)
                .width(220.dp),
            onClick = { chickListItems = chickListItems + listOf("") }
        )

        Spacer(modifier = Modifier.height(100.dp))

        CreateTripButton(text = "Next",
            buttonColor = Color(0xff4F80FF),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(50.dp)
                .width(320.dp),
            onClick = {
                chickListItems.forEachIndexed { index, item ->
                    viewModel.addChickList(
                        id = tripId,
                        itemName = item,
                        checked = false,
                        checkItemId = GenerateRandomIdNumber().toString()
                    )
                }
                if (screenRoot.isEmpty()) {
                    navController.navigate("AddPlaces/$tripId")
                } else if (screenRoot == NavigationRoutes.TRIP_DETAILS) {
                    navController.navigate("tripDetails/$tripId")
                } else {
                    navController.navigate("AddPlaces/$tripId")
                }
            }
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckLitItem(text: String, onTextChanged: (String) -> Unit) {
    Spacer(modifier = Modifier.height(15.dp))
    TextField(
        value = text,
        onValueChange = {
            onTextChanged(it)
        },
        label = {
            Text(text = "My Passport")
        },
        textStyle = TextStyle(
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
}